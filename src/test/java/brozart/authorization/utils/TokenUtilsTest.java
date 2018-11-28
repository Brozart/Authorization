package brozart.authorization.utils;

import brozart.authorization.registration.RegistrationToken;
import brozart.authorization.resetPassword.ResetPasswordToken;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TokenUtilsTest {

    @Test
    public void testUpdateToken_registration() {
        final RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setToken("oldToken");
        final Date controlDate = DateUtils.addDays(new Date(), 1);

        TokenUtils.updateToken(registrationToken, "newToken");
        assertEquals("newToken", registrationToken.getToken());
        assertTrue(DateUtils.isSameDay(controlDate, registrationToken.getExpiryDate()));
    }

    @Test
    public void testUpdateToken_resetPassword() {
        final ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken("oldToken");
        final Date controlDate = DateUtils.addDays(new Date(), 1);

        TokenUtils.updateToken(resetPasswordToken, "newToken");
        assertEquals("newToken", resetPasswordToken.getToken());
        assertTrue(DateUtils.isSameDay(controlDate, resetPasswordToken.getExpiryDate()));
    }

    @Test
    public void testIsTokenExpired() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);

        assertTrue(TokenUtils.isTokenExpired(cal.getTime()));

        cal.add(Calendar.MONTH, 1);
        assertFalse(TokenUtils.isTokenExpired(cal.getTime()));
    }
}
