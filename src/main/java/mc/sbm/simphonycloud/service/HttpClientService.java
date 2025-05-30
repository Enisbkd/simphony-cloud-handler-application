package mc.sbm.simphonycloud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mc.sbm.simphonycloud.exception.HttpClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpClientService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${simphony.transactional.hostname}")
    private String hostname;

    public <T, R> CompletableFuture<R> executeHttpRequest(
        HttpMethod method,
        String basePath,
        String endpoint,
        Map<String, String> pathParams,
        Map<String, String> queryParams,
        HttpHeaders headers,
        String apiKey,
        String bearerToken,
        T requestBody,
        Class<R> responseType
    ) {
        String fullPath = basePath + endpoint;
        log.info("Executing HTTP {} request to: {}", method, fullPath);

        Mono<String> requestMono = buildRequest(method, fullPath, pathParams, queryParams, headers, apiKey, bearerToken, requestBody);

        return executeRequest(requestMono, responseType, method.name(), fullPath);
    }

    private <T> Mono<String> buildRequest(
        HttpMethod method,
        String fullPath,
        Map<String, String> pathParams,
        Map<String, String> queryParams,
        HttpHeaders headers,
        String apiKey,
        String bearerToken,
        T requestBody
    ) {
        return webClient
            .method(method)
            .uri(uriBuilder -> {
                var builder = uriBuilder.scheme("https").host(hostname).path(fullPath);
                if (queryParams != null) {
                    queryParams.forEach(builder::queryParam);
                }
                return builder.build(pathParams != null ? pathParams : Map.of());
            })
            .headers(httpHeaders -> {
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                httpHeaders.set("x-api-key", apiKey);
                httpHeaders.setBearerAuth(bearerToken);
                if (headers != null) {
                    httpHeaders.addAll(headers);
                }
            })
            .body(hasBody(method) && requestBody != null ? Mono.just(requestBody) : Mono.empty(), Object.class)
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError() || status.is5xxServerError(),
                clientResponse ->
                    clientResponse
                        .bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            log.error("HTTP error response: {}", errorBody);
                            return Mono.error(
                                new HttpClientException(
                                    "HTTP error: " + clientResponse.statusCode().value(),
                                    clientResponse.statusCode(),
                                    errorBody
                                )
                            );
                        })
            )
            .bodyToMono(String.class)
            .retryWhen(
                Retry.backoff(3, Duration.ofSeconds(1))
                    .filter(this::isRetryableError)
                    .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                        new HttpClientException("Max retries reached", retrySignal.failure())
                    )
            );
    }

    private <T> CompletableFuture<T> executeRequest(Mono<String> requestMono, Class<T> responseType, String method, String url) {
        return requestMono
            .doOnSuccess(response -> log.info("HTTP {} request to {} succeeded", method, url))
            .doOnError(error -> log.error("HTTP {} request to {} failed: {}", method, url, error.getMessage()))
            .map(response -> deserializeResponse(response, responseType))
            .onErrorMap(this::mapException)
            .toFuture();
    }

    private <T> T deserializeResponse(String response, Class<T> responseType) {
        if (responseType == String.class) {
            return responseType.cast(response);
        }

        try {
            return objectMapper.readValue(response, responseType);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize response: {}", e.getMessage());
            throw new HttpClientException("Failed to deserialize response", e);
        }
    }

    private Throwable mapException(Throwable throwable) {
        if (throwable instanceof HttpClientException) {
            return throwable;
        }

        if (throwable instanceof WebClientResponseException ex) {
            log.error("WebClientResponseException - Status: {}, Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            return new HttpClientException("HTTP error", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
        }

        log.error("Unexpected exception occurred", throwable);
        return new HttpClientException("Unexpected error occurred", throwable);
    }

    private boolean isRetryableError(Throwable throwable) {
        if (throwable instanceof WebClientResponseException ex) {
            HttpStatusCode status = ex.getStatusCode();
            return status.is5xxServerError() || status.value() == 408 || status.value() == 429;
        }
        return false;
    }

    private boolean hasBody(HttpMethod method) {
        return method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH;
    }
    //    private String buildUrl(String baseUrl, String endpoint) {
    //        if (!baseUrl.matches("^https?://.*")) {
    //            throw new IllegalArgumentException("Base URL must start with http:// or https://");
    //        }
    //
    //        if (baseUrl.endsWith("/") && endpoint.startsWith("/")) {
    //            return baseUrl + endpoint.substring(1);
    //        } else if (!baseUrl.endsWith("/") && !endpoint.startsWith("/")) {
    //            return baseUrl + "/" + endpoint;
    //        }
    //        return baseUrl + endpoint;
    //    }

    //    public <T> CompletableFuture<T> get(String baseUrl, String endpoint, Class<T> responseType, HttpHeaders headers) {
    //        return executeHttpRequest(HttpMethod.GET, baseUrl, endpoint, null, null, headers, null, responseType);
    //    }
    //
    //    public <T, R> CompletableFuture<R> post(String baseUrl, String endpoint, T requestBody, Class<R> responseType) {
    //        return executeHttpRequest(HttpMethod.POST, baseUrl, endpoint, null, null, null, requestBody, responseType);
    //    }
    //
    //    public <T, R> CompletableFuture<R> put(String baseUrl, String endpoint, T requestBody, Class<R> responseType) {
    //        return executeHttpRequest(HttpMethod.PUT, baseUrl, endpoint, null, null, null, requestBody, responseType);
    //    }
    //
    //    public <T> CompletableFuture<T> delete(String baseUrl, String endpoint, Class<T> responseType) {
    //        return executeHttpRequest(HttpMethod.DELETE, baseUrl, endpoint, null, null, null, null, responseType);
    //    }
}
