package brozart.authorization.resetPassword;

import brozart.authorization.dto.PasswordResetConfirmationRequest;
import brozart.authorization.dto.PasswordResetTokenRequest;
import brozart.authorization.exception.EmailNotFoundException;
import brozart.authorization.exception.InvalidResetPasswordTokenException;
import brozart.authorization.mail.MailService;
import brozart.authorization.user.User;
import brozart.authorization.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/passwordReset")
public class ResetPasswordController {

    private final ResetPasswordTokenService resetPasswordTokenService;
    private final UserService userService;
    private final MailService mailService;

    @Autowired
    public ResetPasswordController(final ResetPasswordTokenService resetPasswordTokenService, final UserService userService, final MailService mailService) {
        this.resetPasswordTokenService = resetPasswordTokenService;
        this.userService = userService;
        this.mailService = mailService;
    }


    @PostMapping
    public ResponseEntity<?> requestPasswordReset(final PasswordResetTokenRequest request) throws EmailNotFoundException {
        final String email = request.getEmail();
        final User user = userService.findByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(email);
        }

        final ResetPasswordToken resetPasswordToken = resetPasswordTokenService.createForUser(user);
        mailService.sendTokenMail("Password reset", "Password reset url: ",
                request.getPasswordResetUrl(), resetPasswordToken.getToken(), user.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(final String token) throws Exception {
        // Service will throw exception when token is not valid
        resetPasswordTokenService.validate(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> resetPasswordConfirmation(final PasswordResetConfirmationRequest request) throws InvalidResetPasswordTokenException {
        final ResetPasswordToken token = resetPasswordTokenService.findByToken(request.getToken());
        if (token == null) {
            throw new InvalidResetPasswordTokenException();
        }

        final User user = token.getUser();
        user.setPassword(new BCryptPasswordEncoder(10).encode(request.getNewPassword()));
        userService.save(user);

        resetPasswordTokenService.delete(token);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
