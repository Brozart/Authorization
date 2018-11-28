package brozart.authorization.registration;

import brozart.authorization.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RegistrationTokenRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;

    @Test
    public void test() {
        final String token = "token";
        final RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setExpiryDate(new Date());
        registrationToken.setToken(token);

        final User user = new User();
        user.setUsername("username");
        user.setEmail("email");
        final User savedUser = testEntityManager.merge(user);
        registrationToken.setUser(savedUser);

        final RegistrationToken saved = testEntityManager.merge(registrationToken);
        testEntityManager.flush();
        
        final RegistrationToken result = registrationTokenRepository.findByToken(token);
        assertEquals(saved.getId(), result.getId());
        assertEquals(token, result.getToken());
        assertEquals(saved.getExpiryDate(), result.getExpiryDate());
    }
}
