package brozart.authorization.registration;

import brozart.authorization.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationTokenServiceTest {

    @Mock
    private RegistrationTokenRepository repository;

    @InjectMocks
    private RegistrationTokenService registrationTokenService;

    @Test
    public void testCreateForUser() {
        final User user = new User();
        final RegistrationToken registrationToken = new RegistrationToken();

        when(repository.save(any(RegistrationToken.class))).thenReturn(registrationToken);

        final RegistrationToken result = registrationTokenService.createForUser(user);
        assertEquals(registrationToken, result);
    }

    @Test
    public void testRefreshToken() {
        final String token = "token";
        final RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setToken(token);

        when(repository.findByToken(token)).thenReturn(registrationToken);
        when(repository.save(registrationToken)).thenReturn(registrationToken);

        final RegistrationToken result = registrationTokenService.refreshToken(token);
        assertNotEquals(token, result.getToken());
    }

    @Test
    public void testDelete() {
        final RegistrationToken registrationToken = new RegistrationToken();

        repository.delete(registrationToken);

        registrationTokenService.delete(registrationToken);
    }

    @Test
    public void testFindByToken() {
        final String token = "token";
        final RegistrationToken registrationToken = new RegistrationToken();

        when(repository.findByToken(token)).thenReturn(registrationToken);

        final RegistrationToken result = registrationTokenService.findByToken(token);
        assertEquals(registrationToken, result);
    }
}
