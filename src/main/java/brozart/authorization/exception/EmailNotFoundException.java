package brozart.authorization.exception;

/**
 * Custom exception for when user is not found by email.
 */
public class EmailNotFoundException extends Exception {

    /**
     * Creates a new {@link EmailNotFoundException}.
     *
     * @param email the email
     */
    public EmailNotFoundException(final String email) {
        super("User with email " + email + "not found");
    }
}
