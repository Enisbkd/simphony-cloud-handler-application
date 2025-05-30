package mc.sbm.simphonycloud.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.util.UUID;
import mc.sbm.simphonycloud.exception.AuthenticationException;
import mc.sbm.simphonycloud.service.dto.SignInResponse;
import mc.sbm.simphonycloud.utils.CookieUtils;
import mc.sbm.simphonycloud.utils.SignInResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SimphonySignInService {

    private static final Logger log = LoggerFactory.getLogger(SimphonySignInService.class);

    @Value("${simphony.oidc.hostname}")
    private String oidc_hostname;

    @Value("${simphony.orgShortName}")
    private String orgshortname;

    @Value("${simphony.oidc.api_key}")
    private String oidc_api_key;

    private final CookieUtils cookieUtils;
    private final WebClient webClient;

    @Value("${simphony.oidc.hostname}")
    private String oidcHostname;

    @Value("${simphony.oidc.api_key}")
    private String oidcApiKey;

    @Value("${simphony.orgShortName}")
    private String orgShortName;

    private final Timer signInTimer;

    private final MeterRegistry meterRegistry;

    private static final String METRIC_PREFIX = "simphony.auth.";

    public SimphonySignInService(CookieUtils cookieUtils, WebClient webClient, MeterRegistry meterRegistry) {
        this.cookieUtils = cookieUtils;
        this.webClient = webClient;
        this.signInTimer = meterRegistry.timer(METRIC_PREFIX + "signin");
        this.meterRegistry = meterRegistry;
    }

    public Mono<SignInResponse> signIn(String clientId, String username, String password) {
        String requestId = UUID.randomUUID().toString();
        log.info("Starting sign-in process [requestId={}] for username={}", requestId, username);

        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            log.error("Invalid credentials provided [requestId={}]", requestId);
            return Mono.error(new AuthenticationException("Username and password cannot be empty"));
        }

        if (!StringUtils.hasText(orgShortName)) {
            log.error("Organization short name not configured [requestId={}]", requestId);
            return Mono.error(new AuthenticationException("Organization short name is not configured"));
        }

        String cookieHeader = cookieUtils.readCookiesFromCurlFile(clientId);
        if (!StringUtils.hasText(cookieHeader)) {
            log.error("No cookies found for sign-in [requestId={}]. Initialize auth first.", requestId);
            return Mono.error(new AuthenticationException("No authentication cookies found. Please initiate authentication first."));
        }

        log.debug("Using cookie header [requestId={}]: {}", requestId, SimphonyAuthUtils.maskCookieValues(cookieHeader));

        return signInTimer.record(() ->
            webClient
                .post()
                .uri(oidcHostname + "/oidc-provider/v1/oauth2/signin")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("X-API-KEY", oidcApiKey)
                .header("X-Request-ID", requestId)
                .header(HttpHeaders.COOKIE, cookieHeader)
                .body(BodyInserters.fromFormData("username", username).with("password", password).with("orgname", orgShortName))
                .retrieve()
                .onStatus(
                    status -> status.isError(),
                    response -> {
                        log.error("Sign-in failed [requestId={}] with status: {}", requestId, response.statusCode());
                        return Mono.error(new AuthenticationException("Sign-in failed with status: " + response.statusCode()));
                    }
                )
                .bodyToMono(SignInResponse.class)
                .doOnSuccess(response -> {
                    if (response != null && StringUtils.hasText(SignInResponseUtils.extractAuthCode(response.getRedirectUrl()))) {
                        log.info("Sign-in successful [requestId={}], auth code received", requestId);
                        log.debug(
                            "Auth code [requestId={}]: {}",
                            requestId,
                            SimphonyAuthUtils.maskAuthCode(SignInResponseUtils.extractAuthCode(response.getRedirectUrl()))
                        );
                    } else {
                        log.warn("Sign-in completed [requestId={}] but no auth code received", requestId);
                    }
                })
                .doOnError(error -> log.error("Error during sign-in [requestId={}]: {}", requestId, error.getMessage(), error))
                .retryWhen(SimphonyAuthUtils.createRetrySpec("signIn"))
        );
    }
}
