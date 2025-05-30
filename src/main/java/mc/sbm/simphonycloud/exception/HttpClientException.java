package mc.sbm.simphonycloud.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class HttpClientException extends RuntimeException {

    private final HttpStatusCode status;
    private final String responseBody;

    public HttpClientException(String message) {
        super(message);
        this.status = null;
        this.responseBody = null;
    }

    public HttpClientException(String message, Throwable cause) {
        super(message, cause);
        this.status = null;
        this.responseBody = null;
    }

    public HttpClientException(String message, HttpStatusCode status, String responseBody) {
        super(message);
        this.status = status;
        this.responseBody = responseBody;
    }

    public HttpClientException(String message, HttpStatusCode status, String responseBody, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.responseBody = responseBody;
    }
}
