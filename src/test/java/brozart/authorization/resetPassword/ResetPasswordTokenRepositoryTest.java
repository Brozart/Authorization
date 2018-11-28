package brozart.authorization.resetPassword;

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
public class ResetPasswordTokenRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Test
    public void testFindByToken() {
        final String token = "token";
        final User user = new User();
        user.setUsername("username");
        user.setPassword("pwd");
        user.setEmail("email");
        final User savedUser = testEntityManager.merge(user);

        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(token);
        resetPasswordToken.setExpiryDate(new Date());
        resetPasswordToken.setUser(savedUser);

        final ResetPasswordToken saved = testEntityManager.merge(resetPasswordToken);
        testEntityManager.flush();

        final ResetPasswordToken result = resetPasswordTokenRepository.findByToken(token);
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getToken(), result.getToken());
    }

    @Test
    public void testFindByUser() {
        final String token = "token";
        final User user = new User();
        user.setUsername("username");
        user.setPassword("pwd");
        user.setEmail("email");
        final User savedUser = testEntityManager.merge(user);

        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(token);
        resetPasswordToken.setExpiryDate(new Date());
        resetPasswordToken.setUser(savedUser);

        final ResetPasswordToken saved = testEntityManager.merge(resetPasswordToken);
        testEntityManager.flush();

        final ResetPasswordToken result = resetPasswordTokenRepository.findByUser(savedUser);
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getToken(), result.getToken());
        assertEquals(savedUser, result.getUser());
    }
}
