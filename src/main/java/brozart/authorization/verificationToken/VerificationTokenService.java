package brozart.authorization.verificationToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(final VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Transactional
    public VerificationToken save(final VerificationToken verificationToken) {
        return verificationTokenRepository.save(verificationToken);
    }

    @Transactional
    public void delete(final VerificationToken verificationToken) {
        verificationTokenRepository.delete(verificationToken);
    }

    public VerificationToken findByToken(final String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
