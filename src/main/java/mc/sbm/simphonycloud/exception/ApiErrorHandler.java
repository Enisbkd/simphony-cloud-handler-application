package mc.sbm.simphonycloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component
public class ApiErrorHandler {

    public <T> Mono<T> handleResponse(ClientResponse response) {
        return response
            .bodyToMono(String.class)
            .flatMap(body -> Mono.error(new ApiException("API Error: " + body, (HttpStatus) response.statusCode())));
    }
}
