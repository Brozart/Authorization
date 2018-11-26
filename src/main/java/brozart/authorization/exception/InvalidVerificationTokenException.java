package brozart.authorization.exception;

/**
 * Custom exception for an invalid verification token.
 */
public class InvalidVerificationTokenException extends Exception {

    /**
     * Creates a new {@link InvalidVerificationTokenException}.
     */
    public InvalidVerificationTokenException() {
        super("Invalid verification token");
    }
}
