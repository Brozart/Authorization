package brozart.authorization.exception;

/**
 * Custom exception for an invalid registration token.
 */
public class InvalidRegistrationTokenException extends Exception {

    /**
     * Creates a new {@link InvalidRegistrationTokenException}.
     */
    public InvalidRegistrationTokenException() {
        super("Invalid registration token");
    }
}
