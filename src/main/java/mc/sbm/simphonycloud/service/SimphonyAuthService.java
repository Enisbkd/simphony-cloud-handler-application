package mc.sbm.simphonycloud.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import mc.sbm.simphonycloud.exception.AuthenticationException;
import mc.sbm.simphonycloud.utils.CookieUtils;
import mc.sbm.simphonycloud.utils.PkceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class SimphonyAuthService {

    private static final Logger log = LoggerFactory.getLogger(SimphonyAuthService.class);

    @Value("${simphony.oidc.hostname}")
    private String oidcHostname;

    @Value("${simphony.oidc.api_key}")
    private String oidcApiKey;

    private final CookieUtils cookieUtils;

    private final WebClient webClient;

    private final Timer initiateAuthTimer;

    private static final int MAX_RETRIES = 2;
    private static final long RETRY_BACKOFF_MS = 500;

    @Value("${simphony.auth.retry-enabled:true}")
    private boolean retryEnabled;

    private final MeterRegistry meterRegistry;

    private static final String METRIC_PREFIX = "simphony.auth.";

    public SimphonyAuthService(CookieUtils cookieUtils, WebClient webClient, MeterRegistry meterRegistry) {
        this.cookieUtils = cookieUtils;
        this.webClient = webClient;
        this.initiateAuthTimer = meterRegistry.timer(METRIC_PREFIX + "auth");

        this.meterRegistry = meterRegistry;
    }

    public Mono<List<String>> initiateAuth(String clientId) {
        String requestId = UUID.randomUUID().toString();
        log.info("Starting authentication initiation [requestId={}] for clientId={}", requestId, clientId);

        if (!StringUtils.hasText(clientId)) {
            log.error("Invalid clientId provided [requestId={}]", requestId);
            return Mono.error(new AuthenticationException("Client ID cannot be empty"));
        }

        String url = UriComponentsBuilder.fromHttpUrl(oidcHostname + "/oidc-provider/v1/oauth2/authorize")
            .queryParam("response_type", "code")
            .queryParam("client_id", clientId)
            .queryParam("scope", "openid")
            .queryParam("redirect_uri", "apiaccount://callback")
            .queryParam("code_challenge", PkceUtil.getCodeChallenge())
            .queryParam("code_challenge_method", "S256")
            .queryParam("state", "999")
            .build(true)
            .toUriString();

        log.debug("Auth URL constructed [requestId={}]: {}", requestId, url);

        return initiateAuthTimer.record(() ->
            webClient
                .get()
                .uri(url)
                .header("X-API-KEY", oidcApiKey)
                .header("X-Request-ID", requestId)
                .exchangeToMono(response -> {
                    log.debug("Received response [requestId={}] with status {}", requestId, response.statusCode());

                    if (response.statusCode().is2xxSuccessful() || response.statusCode().is3xxRedirection()) {
                        List<String> cookies = response.headers().asHttpHeaders().get(HttpHeaders.SET_COOKIE);
                        if (cookies != null && !cookies.isEmpty()) {
                            log.debug("Received {} cookies [requestId={}]", cookies.size(), requestId);
                            cookieUtils.writeCookiesToCurlFormat(cookies, clientId);
                            log.info("Cookies stored successfully [requestId={}]", requestId);
                            return Mono.just(cookies);
                        } else {
                            log.warn("No cookies received in response [requestId={}]", requestId);
                            return Mono.error(new AuthenticationException("No cookies received from authentication server"));
                        }
                    } else {
                        log.error("Failed to initiate auth [requestId={}] with status: {}", requestId, response.statusCode());
                        return Mono.error(
                            new AuthenticationException("Failed to initiate authentication. Status: " + response.statusCode())
                        );
                    }
                })
                .doOnError(error -> log.error("Error during initiateAuth [requestId={}]: {}", requestId, error.getMessage(), error))
                .retryWhen(createRetrySpec("initiateAuth"))
        );
    }

    private Retry createRetrySpec(String operationName) {
        if (!retryEnabled) return Retry.max(0);

        return Retry.backoff(MAX_RETRIES, Duration.ofMillis(RETRY_BACKOFF_MS))
            .filter(throwable -> {
                if (throwable instanceof WebClientResponseException wcre) {
                    return wcre.getStatusCode().is5xxServerError();
                }
                return !(throwable instanceof AuthenticationException);
            })
            .doBeforeRetry(retrySignal ->
                log.warn("{} failed, attempting retry {}/{}", operationName, retrySignal.totalRetries() + 1, MAX_RETRIES)
            );
    }
}
