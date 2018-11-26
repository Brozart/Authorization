package brozart.authorization.exception;

/**
 * Custom exception for when a user is registered with an existing email.
 */
public class EmailAlreadyInUseException extends Exception {

    /**
     * Creates a new {@link EmailAlreadyInUseException}.
     */
    public EmailAlreadyInUseException() {
        super("User with given email already exists");
    }
}
