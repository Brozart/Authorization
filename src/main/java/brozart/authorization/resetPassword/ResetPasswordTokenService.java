package brozart.authorization.resetPassword;

import brozart.authorization.user.User;
import brozart.authorization.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ResetPasswordTokenService {

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    public ResetPasswordTokenService(final ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @Transactional
    public ResetPasswordToken createForUser(final User user) {
        final ResetPasswordToken existing = findByUser(user);
        if (existing != null) {
            TokenUtils.updateToken(existing, UUID.randomUUID().toString());
            return resetPasswordTokenRepository.save(existing);
        } else {
            final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
            resetPasswordToken.setUser(user);
            TokenUtils.updateToken(resetPasswordToken, UUID.randomUUID().toString());
            return resetPasswordTokenRepository.save(resetPasswordToken);
        }
    }

    @Transactional
    public ResetPasswordToken save(final ResetPasswordToken resetPasswordToken) {
        return resetPasswordTokenRepository.save(resetPasswordToken);
    }

    private ResetPasswordToken findByUser(final User user) {
        return resetPasswordTokenRepository.findByUser(user);
    }
}
