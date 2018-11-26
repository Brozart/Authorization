package brozart.authorization.exception;

public class EmailExistsException extends Exception {

    /**
     * Creates a new {@link EmailExistsException}.
     */
    public EmailExistsException() {
        super("User with given email already exists");
    }
}
