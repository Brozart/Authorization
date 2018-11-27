package brozart.authorization.resetPassword;

import brozart.authorization.user.User;
import org.springframework.data.repository.CrudRepository;

public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, Long> {

    /**
     * Finds the {@link ResetPasswordToken} by given token.
     *
     * @return the found {@link ResetPasswordToken}
     */
    ResetPasswordToken findByToken(String token);

    /**
     * Finds the {@link ResetPasswordToken} by given {@link User}.
     *
     * @return the found {@link ResetPasswordToken}
     */
    ResetPasswordToken findByUser(User user);
}
