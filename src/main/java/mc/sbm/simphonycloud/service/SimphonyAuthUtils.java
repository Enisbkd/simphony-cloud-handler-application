package mc.sbm.simphonycloud.service;

import java.time.Duration;
import lombok.Getter;
import mc.sbm.simphonycloud.exception.AuthenticationException;
import mc.sbm.simphonycloud.service.dto.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

@Service
public class SimphonyAuthUtils {

    private static final Logger log = LoggerFactory.getLogger(SimphonyAuthUtils.class);
    private static final int MAX_RETRIES = 2;
    private static final long RETRY_BACKOFF_MS = 500;

    @Value("${simphony.auth.retry-enabled:true}")
    private static boolean retryEnabled;

    @Getter
    private volatile TokenResponse currentToken;

    public SimphonyAuthUtils() {}

    static Retry createRetrySpec(String operationName) {
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

    public static String maskToken(String token) {
        if (token == null || token.length() <= 8) return "***";
        return token.substring(0, 4) + "..." + token.substring(token.length() - 4);
    }

    static String maskAuthCode(String code) {
        if (code == null || code.length() <= 4) return "***";
        return code.substring(0, 2) + "..." + code.substring(code.length() - 2);
    }

    static String maskCookieValues(String cookieHeader) {
        if (cookieHeader == null) return null;
        return cookieHeader.replaceAll("=([^;]+)", "=***");
    }

    public void clearToken() {
        this.currentToken = null;
    }
}
