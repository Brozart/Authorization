package brozart.authorization.registration;

import org.springframework.data.repository.CrudRepository;

public interface RegistrationTokenRepository extends CrudRepository<RegistrationToken, Long> {

    /**
     * Finds the {@link RegistrationToken} by given token.
     *
     * @param token the token
     * @return the verification token
     */
    RegistrationToken findByToken(String token);
}
