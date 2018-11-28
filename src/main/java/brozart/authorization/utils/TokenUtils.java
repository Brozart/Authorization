package brozart.authorization.utils;

import brozart.authorization.registration.RegistrationToken;
import brozart.authorization.resetPassword.ResetPasswordToken;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class TokenUtils {

    public static void updateToken(final RegistrationToken registrationToken, final String token) {
        registrationToken.setToken(token);
        registrationToken.setExpiryDate(calculateNewExpiryDate());
    }

    public static void updateToken(final ResetPasswordToken resetPasswordToken, final String token) {
        resetPasswordToken.setToken(token);
        resetPasswordToken.setExpiryDate(calculateNewExpiryDate());
    }

    private static Date calculateNewExpiryDate() {
        return DateUtils.addDays(new Date(), 1);
    }

    public static boolean isTokenExpired(final Date expiryDate) {
        final Calendar cal = Calendar.getInstance();
        final Date now = cal.getTime();
        return now.compareTo(expiryDate) >= 0;
    }
}
