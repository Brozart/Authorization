package brozart.authorization.registration;

import brozart.authorization.user.User;
import brozart.authorization.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegistrationTokenService {

    private final RegistrationTokenRepository registrationTokenRepository;

    @Autowired
    public RegistrationTokenService(final RegistrationTokenRepository registrationTokenRepository) {
        this.registrationTokenRepository = registrationTokenRepository;
    }

    @Transactional
    public RegistrationToken createForUser(final User user) {
        final RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setUser(user);
        TokenUtils.updateToken(registrationToken, UUID.randomUUID().toString());
        return registrationTokenRepository.save(registrationToken);
    }

    @Transactional
    public RegistrationToken refreshToken(final String token) {
        final RegistrationToken registrationToken = findByToken(token);
        TokenUtils.updateToken(registrationToken, UUID.randomUUID().toString());
        return registrationTokenRepository.save(registrationToken);
    }

    @Transactional
    public void delete(final RegistrationToken registrationToken) {
        registrationTokenRepository.delete(registrationToken);
    }


    RegistrationToken findByToken(final String token) {
        return registrationTokenRepository.findByToken(token);
    }
}
