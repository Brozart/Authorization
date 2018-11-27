package brozart.authorization.exception;

/**
 * Custom exception for an expired registration token.
 */
public class ExpiredRegistrationTokenException extends Exception {

    /**
     * Creates a new {@link ExpiredRegistrationTokenException}.
     */
    public ExpiredRegistrationTokenException() {
        super("Registration token is expired");
    }
}
