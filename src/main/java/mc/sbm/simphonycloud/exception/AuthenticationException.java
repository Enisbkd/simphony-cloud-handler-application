package mc.sbm.simphonycloud.exception;

/**
 * Exception thrown when authentication-related operations fail.
 * This provides specific error handling for authentication flows.
 */
public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs an AuthenticationException with the specified detail message.
     *
     * @param message the detail message
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructs an AuthenticationException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
