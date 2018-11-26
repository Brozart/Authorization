package brozart.authorization.verificationToken;

import brozart.authorization.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

@Service
public class VerificationTokenService {

    private static final int EXPIRATION = 60 * 24;

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(final VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Transactional
    public VerificationToken create(final User user) {
        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(UUID.randomUUID().toString());
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        verificationToken.setExpiryDate(cal.getTime());
        return verificationTokenRepository.save(verificationToken);
    }

    public VerificationToken findByToken(final String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Transactional
    public void delete(final VerificationToken verificationToken) {
        verificationTokenRepository.delete(verificationToken);
    }
}
