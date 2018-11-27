package brozart.authorization.exception;

/**
 * Custom exception for an invalid registration token.
 */
public class InvalidRegistrationToken extends Exception {

    /**
     * Creates a new {@link InvalidRegistrationToken}.
     */
    public InvalidRegistrationToken() {
        super("Invalid registration token");
    }
}
