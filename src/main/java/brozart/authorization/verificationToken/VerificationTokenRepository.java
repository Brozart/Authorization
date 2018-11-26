package brozart.authorization.verificationToken;

import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    /**
     * Finds the {@link VerificationToken} by given token.
     *
     * @param token the token
     * @return the verification token
     */
    VerificationToken findByToken(String token);
}
