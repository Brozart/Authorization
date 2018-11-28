package brozart.authorization.user;

import brozart.authorization.exception.EmailAlreadyInUseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testLoadUserByUserName() {
        final String username = "username";
        final User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        final UserDetails userDetails = userService.loadUserByUsername(username);
        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUserName_NotFound() {
        final String username = "username";

        when(userRepository.findByUsername(username)).thenReturn(null);

        userService.loadUserByUsername(username);
    }

    @Test
    public void testRegisterNewUserAccount() throws EmailAlreadyInUseException {
        final User user = new User();
        user.setEmail("email");
        user.setPassword("pwd");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        final User result = userService.registerNewUserAccount(user);
        assertNotNull(result);
        assertNotNull(result.getPassword());
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test(expected = EmailAlreadyInUseException.class)
    public void testRegisterNewUserAccount_EmailInUse() throws EmailAlreadyInUseException {
        final User user = new User();
        user.setEmail("email");
        user.setPassword("pwd");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        userService.registerNewUserAccount(user);
    }

    @Test
    public void testSave() {
        final User user = new User();
        user.setEmail("email");
        user.setPassword("pwd");

        when(userRepository.save(user)).thenReturn(user);

        final User result = userService.save(user);
        assertEquals(user, result);
    }

    @Test
    public void testFindByEmail() {
        final String email = "mail";
        final User found = new User();

        when(userRepository.findByEmail(email)).thenReturn(found);

        final User result = userService.findByEmail(email);
        assertEquals(found, result);
    }
}
