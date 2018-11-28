package brozart.authorization.registration;

import brozart.authorization.dto.RegisterTokenRequest;
import brozart.authorization.dto.UserDTO;
import brozart.authorization.exception.EmailAlreadyInUseException;
import brozart.authorization.exception.ExpiredRegistrationTokenException;
import brozart.authorization.exception.InvalidRegistrationTokenException;
import brozart.authorization.mail.MailService;
import brozart.authorization.user.User;
import brozart.authorization.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

    @Mock
    private RegistrationTokenService registrationTokenService;
    @Mock
    private UserService userService;
    @Mock
    private MailService mailService;

    @InjectMocks
    private RegistrationController registrationController;

    @Test
    public void testRegister() throws EmailAlreadyInUseException {
        final UserDTO userDTO = new UserDTO();
        userDTO.setEmail("email");
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        userDTO.setVerificationBaseUrl("url");
        final User user = new User();
        user.setEmail(userDTO.getEmail());
        final RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setToken("token");

        when(userService.registerNewUserAccount(any(User.class))).thenReturn(user);
        when(registrationTokenService.createForUser(user)).thenReturn(registrationToken);
        mailService.sendTokenMail("Registration", "Registration successful: confirm here: ",
                userDTO.getVerificationBaseUrl(), registrationToken.getToken(), user.getEmail());

        final ResponseEntity<?> register = registrationController.register(userDTO);
        assertEquals(HttpStatus.OK, register.getStatusCode());
    }

    @Test(expected = EmailAlreadyInUseException.class)
    public void testRegister_EmailAlreadyInUser() throws EmailAlreadyInUseException {
        final UserDTO userDTO = new UserDTO();
        userDTO.setEmail("email");
        userDTO.setUsername("username");
        userDTO.setPassword("password");
        userDTO.setVerificationBaseUrl("url");

        when(userService.registerNewUserAccount(any(User.class))).thenThrow(new EmailAlreadyInUseException());

        registrationController.register(userDTO);
    }

    @Test
    public void testGenerateNewRegisterToken() {
        final RegisterTokenRequest registerTokenRequest = new RegisterTokenRequest();
        registerTokenRequest.setToken("token");
        registerTokenRequest.setRegistrationBaseUrl("Registration base url");

        final User user = new User();
        user.setEmail("email");
        final RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setToken("refreshed");
        registrationToken.setUser(user);

        when(registrationTokenService.refreshToken(registerTokenRequest.getToken())).thenReturn(registrationToken);
        mailService.sendTokenMail("Registration", "New registration successful: confirm here: ",
                registerTokenRequest.getRegistrationBaseUrl(), registrationToken.getToken(), user.getEmail());

        final ResponseEntity<?> responseEntity = registrationController.generateNewRegisterToken(registerTokenRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testConfirmRegistration() throws Exception {
        final String token = "token";
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);

        final RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setToken(token);
        registrationToken.setExpiryDate(cal.getTime());
        final User user = new User();
        user.setEnabled(false);
        registrationToken.setUser(user);

        when(registrationTokenService.findByToken(token)).thenReturn(registrationToken);
        userService.save(user);
        registrationTokenService.delete(registrationToken);

        final ResponseEntity<?> responseEntity = registrationController.confirmRegistration(token);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(user.getEnabled());
    }

    @Test(expected = ExpiredRegistrationTokenException.class)
    public void testConfirmRegistration_expired() throws Exception {
        final String token = "token";
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        final RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setToken(token);
        registrationToken.setExpiryDate(cal.getTime());
        final User user = new User();
        user.setEnabled(false);
        registrationToken.setUser(user);

        when(registrationTokenService.findByToken(token)).thenReturn(registrationToken);

        registrationController.confirmRegistration(token);
    }

    @Test(expected = InvalidRegistrationTokenException.class)
    public void testConfirmRegistration_invalid() throws Exception {
        final String token = "token";

        when(registrationTokenService.findByToken(token)).thenReturn(null);

        registrationController.confirmRegistration(token);
    }
}
