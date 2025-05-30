package mc.sbm.simphonycloud.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import mc.sbm.simphonycloud.exception.AuthenticationException;
import mc.sbm.simphonycloud.service.dto.TokenResponse;
import mc.sbm.simphonycloud.utils.CookieUtils;
import mc.sbm.simphonycloud.utils.PkceUtil;
import mc.sbm.simphonycloud.utils.SignInResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SimphonyTokenService {

    private volatile TokenResponse currentBIToken;

    private final Map<String, TokenResponse> tokens = new ConcurrentHashMap<>();

    private volatile TokenResponse currentTransToken;

    private static final Logger log = LoggerFactory.getLogger(SimphonyTokenService.class);

    private final CookieUtils cookieUtils;
    private final Timer getTokenTimer;
    private final MeterRegistry meterRegistry;
    private final Timer refreshTokenTimer;

    @Autowired
    public SimphonyTokenService(
        CookieUtils cookieUtils,
        MeterRegistry meterRegistry,
        WebClient webClient,
        SimphonyAuthService simphonyAuthService,
        SimphonySignInService simphonySignInService
    ) {
        this.cookieUtils = cookieUtils;
        this.meterRegistry = meterRegistry;
        this.refreshTokenTimer = meterRegistry.timer(METRIC_PREFIX + "refresh_token");
        this.webClient = webClient;
        this.getTokenTimer = meterRegistry.timer(METRIC_PREFIX + "get_token");

        this.simphonyAuthService = simphonyAuthService;
        this.simphonySignInService = simphonySignInService;
    }

    private final WebClient webClient;

    private static final String METRIC_PREFIX = "simphony.auth.";

    @Value("${simphony.oidc.hostname}")
    private String oidcHostname;

    @Value("${simphony.oidc.api_key}")
    private String oidcApiKey;

    private final SimphonyAuthService simphonyAuthService;

    private final SimphonySignInService simphonySignInService;

    private final Map<String, ScheduledFuture<?>> refreshTasks = new ConcurrentHashMap<>();

    private volatile long tokenExpiryEpochSeconds;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Mono<TokenResponse> getToken(String clientId, String code) {
        String requestId = UUID.randomUUID().toString();
        log.info("Starting token retrieval [requestId={}] for clientId={}", requestId, clientId);

        if (!StringUtils.hasText(clientId) || !StringUtils.hasText(code)) {
            log.error("Invalid parameters for token retrieval [requestId={}]", requestId);
            return Mono.error(new AuthenticationException("Client ID and authorization code cannot be empty"));
        }

        String codeVerifier = PkceUtil.getCodeVerifier();
        if (!StringUtils.hasText(codeVerifier)) {
            log.error("Missing PKCE code verifier [requestId={}]", requestId);
            return Mono.error(new AuthenticationException("PKCE code verifier is missing"));
        }

        log.debug("Using authorization code [requestId={}]: {}", requestId, SimphonyAuthUtils.maskAuthCode(code));

        String body =
            "grant_type=authorization_code" +
            "&client_id=" +
            clientId +
            "&code_verifier=" +
            codeVerifier +
            "&code=" +
            code +
            "&scope=openid";

        return getTokenTimer.record(() ->
            webClient
                .post()
                .uri(oidcHostname + "/oidc-provider/v1/oauth2/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("X-API-KEY", oidcApiKey)
                .header("X-Request-ID", requestId)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.error("Token retrieval failed [requestId={}]", requestId);
                    return Mono.error(new AuthenticationException("Token retrieval failed with status: " + response.statusCode()));
                })
                .toEntity(TokenResponse.class)
                .flatMap(entity -> {
                    List<String> setCookies = entity.getHeaders().get(HttpHeaders.SET_COOKIE);
                    if (setCookies != null && !setCookies.isEmpty()) {
                        log.debug("Received {} cookies with token [requestId={}]", setCookies.size(), requestId);
                        cookieUtils.writeCookiesToCurlFormat(setCookies, clientId);
                    }
                    TokenResponse tokenResponse = entity.getBody();
                    if (tokenResponse != null) {
                        log.info("Token retrieved successfully [requestId={}]", requestId);
                        log.debug(
                            "Access token [requestId={}]: {}",
                            requestId,
                            SimphonyAuthUtils.maskToken(tokenResponse.getAccess_token())
                        );
                    }
                    tokenResponse.setClientId(clientId);
                    tokenResponse.setCreated_at(LocalDateTime.now());
                    tokenResponse.setExpires_at(LocalDateTime.now().plusSeconds(Long.parseLong(tokenResponse.getExpires_in())));
                    this.tokens.put(clientId, tokenResponse);
                    scheduleTokenRefresh(clientId, tokenResponse);
                    return Mono.justOrEmpty(tokenResponse);
                })
                .doOnError(error -> log.error("Error during token retrieval [requestId={}]: {}", requestId, error.getMessage(), error))
                .retryWhen(SimphonyAuthUtils.createRetrySpec("getToken"))
        );
    }

    public Mono<TokenResponse> refreshToken(String clientId, String refreshToken) {
        String requestId = UUID.randomUUID().toString();
        log.info("Starting token refresh [requestId={}] for clientId={}", requestId, clientId);

        if (!StringUtils.hasText(clientId) || !StringUtils.hasText(refreshToken)) {
            log.error("Invalid parameters for token refresh [requestId={}]", requestId);
            return Mono.error(new AuthenticationException("Client ID and refresh token cannot be empty"));
        }

        log.debug("Using refresh token [requestId={}]: {}", requestId, SimphonyAuthUtils.maskToken(refreshToken));

        String body = "grant_type=refresh_token" + "&client_id=" + clientId + "&refresh_token=" + refreshToken + "&scope=openid";

        return refreshTokenTimer.record(() ->
            webClient
                .post()
                .uri(oidcHostname + "/oidc-provider/v1/oauth2/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("X-API-KEY", oidcApiKey)
                .header("X-Request-ID", requestId)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.error("Token refresh failed [requestId={}]", requestId);
                    return Mono.error(new AuthenticationException("Token refresh failed with status: " + response.statusCode()));
                })
                .toEntity(TokenResponse.class)
                .flatMap(entity -> {
                    List<String> setCookies = entity.getHeaders().get(HttpHeaders.SET_COOKIE);
                    if (setCookies != null && !setCookies.isEmpty()) {
                        log.debug("Received {} cookies with refreshed token [requestId={}]", setCookies.size(), requestId);
                        cookieUtils.writeCookiesToCurlFormat(setCookies, clientId);
                    }
                    TokenResponse tokenResponse = entity.getBody();
                    if (tokenResponse != null && StringUtils.hasText(tokenResponse.getAccess_token())) {
                        log.info("Token refreshed successfully [requestId={}]", requestId);
                        log.debug(
                            "New access token [requestId={}]: {}",
                            requestId,
                            SimphonyAuthUtils.maskToken(tokenResponse.getAccess_token())
                        );
                    }
                    tokenResponse.setCreated_at(LocalDateTime.now());
                    tokenResponse.setExpires_at(LocalDateTime.now().plusSeconds(Long.parseLong(tokenResponse.getExpires_in())));
                    tokenResponse.setClientId(clientId);
                    this.tokens.put(clientId, tokenResponse);
                    scheduleTokenRefresh(clientId, tokenResponse);
                    return Mono.justOrEmpty(tokenResponse);
                })
                .doOnError(error -> log.error("Error during token refresh [requestId={}]: {}", requestId, error.getMessage(), error))
                .retryWhen(SimphonyAuthUtils.createRetrySpec("refreshToken"))
        );
    }

    public Mono<TokenResponse> authenticate(String clientId, String username, String password) {
        String requestId = UUID.randomUUID().toString();
        log.info("Starting complete authentication flow [requestId={}] for user={}, clientId={}", requestId, username, clientId);

        if (!StringUtils.hasText(clientId) || !StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            log.error("Invalid parameters for authentication [requestId={}]", requestId);
            return Mono.error(new AuthenticationException("Client ID, username, and password cannot be empty"));
        }

        return simphonyAuthService
            .initiateAuth(clientId)
            .doOnSuccess(cookies -> log.info("initiateAuth successful with {} cookies [requestId={}]", cookies.size(), requestId))
            .then(Mono.defer(() -> simphonySignInService.signIn(clientId, username, password)))
            .flatMap(signInResponse -> {
                String authCode = SignInResponseUtils.extractAuthCode(signInResponse.getRedirectUrl());
                if (!StringUtils.hasText(authCode)) {
                    log.error("No authorization code received from sign-in [requestId={}]", requestId);
                    return Mono.error(new AuthenticationException("No authorization code received from sign-in"));
                }
                return getToken(clientId, authCode);
            })
            .doOnSuccess(tokenResponse -> log.info("Complete authentication flow successful [requestId={}]", requestId))
            .doOnError(error -> log.error("Complete authentication flow failed [requestId={}]: {}", requestId, error.getMessage(), error));
    }

    private void scheduleTokenRefresh(String clientId, TokenResponse tokenResponse) {
        ScheduledFuture<?> existingTask = refreshTasks.get(clientId);
        if (existingTask != null && !existingTask.isDone()) {
            existingTask.cancel(false);
        }

        long expiresInSeconds = Long.parseLong(tokenResponse.getExpires_in());

        long refreshBeforeExpirySeconds = 3600; // Adjust this value as needed (e.g., 14 days)
        long refreshDelaySeconds = expiresInSeconds - refreshBeforeExpirySeconds;

        if (refreshDelaySeconds <= 0) {
            refreshDelaySeconds = Math.max(expiresInSeconds / 2, 30); // fallback
        }

        log.info("Scheduling token refresh for {} in {} seconds", clientId, refreshDelaySeconds);

        ScheduledFuture<?> task = scheduler.schedule(
            () -> {
                log.info("Refreshing token for client {} before expiry...", clientId);
                refreshCurrentToken(clientId).subscribe(
                    refreshedToken -> {
                        log.info("Token refreshed successfully for {}", clientId);
                        tokens.put(clientId, refreshedToken);
                        scheduleTokenRefresh(clientId, refreshedToken); // Reschedule for next refresh
                    },
                    e -> log.error("Failed to refresh token for {}: {}", clientId, e.getMessage())
                );
            },
            refreshDelaySeconds,
            TimeUnit.SECONDS
        );

        refreshTasks.put(clientId, task);
    }

    public Mono<TokenResponse> refreshCurrentToken(String clientId) {
        TokenResponse tokenResponse = tokens.get(clientId);
        if (tokenResponse == null || !StringUtils.hasText(tokenResponse.getRefresh_token())) {
            return Mono.error(new AuthenticationException("No refresh token available for " + clientId));
        }
        return refreshToken(clientId, tokenResponse.getRefresh_token());
    }

    public TokenResponse getCurrentTokenByClientId(String clientId) {
        return this.tokens.get(clientId);
    }
}
