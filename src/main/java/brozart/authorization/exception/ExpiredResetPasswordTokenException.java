package brozart.authorization.exception;

/**
 * Custom exception for an expired {@link brozart.authorization.resetPassword.ResetPasswordToken}.
 */
public class ExpiredResetPasswordTokenException extends Exception {

    /**
     * Creates a new {@link ExpiredResetPasswordTokenException}.
     */
    public ExpiredResetPasswordTokenException() {
        super("Reset password token is expired");
    }
}
