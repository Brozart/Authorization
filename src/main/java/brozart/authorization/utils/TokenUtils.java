package brozart.authorization.utils;

import brozart.authorization.registration.RegistrationToken;
import brozart.authorization.resetPassword.ResetPasswordToken;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TokenUtils {

    private static final int EXPIRATION = 60 * 24;

    public static void updateToken(final RegistrationToken registrationToken, final String token) {
        registrationToken.setToken(token);
        registrationToken.setExpiryDate(getExpiryDate());
    }

    public static void updateToken(final ResetPasswordToken resetPasswordToken, final String token) {
        resetPasswordToken.setToken(token);
        resetPasswordToken.setExpiryDate(getExpiryDate());
    }

    private static Date getExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return cal.getTime();
    }
}
