package brozart.authorization.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        final User user = new User();
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("pwd");
        final User merge = testEntityManager.merge(user);
        testEntityManager.flush();

        final User result = userRepository.findByUsername(user.getUsername());
        assertNotNull(result);
        assertEquals(merge.getId(), result.getId());
        assertEquals(merge.getUsername(), result.getUsername());
    }

    @Test
    public void testFindByEmail() {
        final User user = new User();
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("pwd");
        final User merge = testEntityManager.merge(user);
        testEntityManager.flush();

        final User result = userRepository.findByEmail(user.getEmail());
        assertNotNull(result);
        assertEquals(merge.getId(), result.getId());
        assertEquals(merge.getEmail(), result.getEmail());
    }
}
