package brozart.authorization.exception;

/**
 * Custom exception for an invalid {@link brozart.authorization.resetPassword.ResetPasswordToken}.
 */
public class InvalidResetPasswordTokenException extends Exception {

    /**
     * Creates a new {@link InvalidResetPasswordTokenException}
     */
    public InvalidResetPasswordTokenException() {
        super("Reset password token is invalid");
    }
}
