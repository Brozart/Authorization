package brozart.authorization.exception;

/**
 * Custom exception for an expired registration token.
 */
public class ExpiredRegistrationToken extends Exception {

    /**
     * Creates a new {@link ExpiredRegistrationToken}.
     */
    public ExpiredRegistrationToken() {
        super("Registration token is expired");
    }
}
