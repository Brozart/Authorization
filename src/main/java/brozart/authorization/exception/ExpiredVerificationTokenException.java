package brozart.authorization.exception;

/**
 * Custom exception for an expired verification token.
 */
public class ExpiredVerificationTokenException extends Exception {

    /**
     * Creates a new {@link ExpiredVerificationTokenException}.
     */
    public ExpiredVerificationTokenException() {
        super("Verification token is expired");
    }
}
