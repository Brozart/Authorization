package brozart.authorization.resetPassword;

import brozart.authorization.exception.ExpiredResetPasswordTokenException;
import brozart.authorization.exception.InvalidResetPasswordTokenException;
import brozart.authorization.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordTokenServiceTest {

    @Mock
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @InjectMocks
    private ResetPasswordTokenService resetPasswordTokenService;

    @Test
    public void testCreateForUser_existing() {
        final User user = new User();
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken("token");

        when(resetPasswordTokenRepository.findByUser(user)).thenReturn(resetPasswordToken);
        when(resetPasswordTokenRepository.save(resetPasswordToken)).thenReturn(resetPasswordToken);

        final ResetPasswordToken result = resetPasswordTokenService.createForUser(user);
        assertNotEquals("token", result.getToken());
    }

    @Test
    public void testCreateForUser_new() {
        final User user = new User();
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken("token");

        when(resetPasswordTokenRepository.findByUser(user)).thenReturn(null);
        when(resetPasswordTokenRepository.save(any(ResetPasswordToken.class))).thenReturn(resetPasswordToken);

        final ResetPasswordToken result = resetPasswordTokenService.createForUser(user);
        assertEquals("token", result.getToken());
    }

    @Test
    public void testValidate() throws Exception {
        final String token = "token";
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(token);
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        resetPasswordToken.setExpiryDate(cal.getTime());

        when(resetPasswordTokenRepository.findByToken(token)).thenReturn(resetPasswordToken);

        resetPasswordTokenService.validate(token);
    }

    @Test(expected = InvalidResetPasswordTokenException.class)
    public void testValidate_invalid() throws Exception {
        final String token = "token";

        when(resetPasswordTokenRepository.findByToken(token)).thenReturn(null);

        resetPasswordTokenService.validate(token);
    }

    @Test(expected = ExpiredResetPasswordTokenException.class)
    public void testValidate_Expired() throws Exception {
        final String token = "token";
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(token);
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        resetPasswordToken.setExpiryDate(cal.getTime());

        when(resetPasswordTokenRepository.findByToken(token)).thenReturn(resetPasswordToken);

        resetPasswordTokenService.validate(token);
    }

    @Test
    public void testFindByToken() {
        final String token = "token";
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();

        when(resetPasswordTokenRepository.findByToken(token)).thenReturn(resetPasswordToken);

        final ResetPasswordToken result = resetPasswordTokenService.findByToken(token);
        assertEquals(resetPasswordToken, result);
    }

    @Test
    public void testDelete() {
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();

        resetPasswordTokenRepository.delete(resetPasswordToken);

        resetPasswordTokenService.delete(resetPasswordToken);
    }
}
