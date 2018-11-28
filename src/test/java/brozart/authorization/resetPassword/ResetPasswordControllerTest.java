package brozart.authorization.resetPassword;

import brozart.authorization.dto.PasswordResetConfirmationRequest;
import brozart.authorization.dto.PasswordResetTokenRequest;
import brozart.authorization.exception.EmailNotFoundException;
import brozart.authorization.exception.InvalidResetPasswordTokenException;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordControllerTest {

    @Mock
    private ResetPasswordTokenService resetPasswordTokenService;
    @Mock
    private UserService userService;
    @Mock
    private MailService mailService;

    @InjectMocks
    private ResetPasswordController resetPasswordController;

    @Test
    public void testRequestPasswordReset() throws EmailNotFoundException {
        final PasswordResetTokenRequest passwordResetTokenRequest = new PasswordResetTokenRequest();
        passwordResetTokenRequest.setEmail("email");
        passwordResetTokenRequest.setPasswordResetUrl("resetUrl");
        final User user = new User();
        user.setEmail(passwordResetTokenRequest.getEmail());
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();

        when(userService.findByEmail(passwordResetTokenRequest.getEmail())).thenReturn(user);
        when(resetPasswordTokenService.createForUser(user)).thenReturn(resetPasswordToken);
        mailService.sendTokenMail("Password reset", "Password reset url: ",
                passwordResetTokenRequest.getPasswordResetUrl(), resetPasswordToken.getToken(), user.getEmail());

        final ResponseEntity<?> result = resetPasswordController.requestPasswordReset(passwordResetTokenRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test(expected = EmailNotFoundException.class)
    public void testRequestPasswordReset_EmailNotFound() throws EmailNotFoundException {
        final PasswordResetTokenRequest passwordResetTokenRequest = new PasswordResetTokenRequest();
        passwordResetTokenRequest.setEmail("email");
        passwordResetTokenRequest.setPasswordResetUrl("resetUrl");

        when(userService.findByEmail(passwordResetTokenRequest.getEmail())).thenReturn(null);

        resetPasswordController.requestPasswordReset(passwordResetTokenRequest);
    }

    @Test
    public void testValidateToken() throws Exception {
        final String token = "token";

        resetPasswordTokenService.validate(token);

        final ResponseEntity<?> responseEntity = resetPasswordController.validateToken(token);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateToken_exception() throws Exception {
        final String token = "token";

        doThrow(IllegalArgumentException.class).when(resetPasswordTokenService).validate(token);

        resetPasswordController.validateToken(token);
    }

    @Test
    public void testResetPassword() throws InvalidResetPasswordTokenException {
        final PasswordResetConfirmationRequest request = new PasswordResetConfirmationRequest();
        request.setToken("token");
        request.setNewPassword("newPassword");
        final User user = new User();
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setUser(user);

        when(resetPasswordTokenService.findByToken(request.getToken())).thenReturn(resetPasswordToken);
        when(userService.save(user)).thenReturn(user);
        resetPasswordTokenService.delete(resetPasswordToken);

        final ResponseEntity<?> responseEntity = resetPasswordController.resetPasswordConfirmation(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(user.getPassword());
    }

    @Test(expected = InvalidResetPasswordTokenException.class)
    public void testResetPassword_InvalidToken() throws InvalidResetPasswordTokenException {
        final PasswordResetConfirmationRequest request = new PasswordResetConfirmationRequest();
        request.setToken("token");
        request.setNewPassword("newPassword");

        when(resetPasswordTokenService.findByToken(request.getToken())).thenReturn(null);

        resetPasswordController.resetPasswordConfirmation(request);
    }
}
