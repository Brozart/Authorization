package brozart.authorization.resetPassword;

import brozart.authorization.dto.PasswordResetRequest;
import brozart.authorization.exception.EmailNotFoundException;
import brozart.authorization.mail.MailService;
import brozart.authorization.user.User;
import brozart.authorization.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> passwordReset(final PasswordResetRequest passwordResetRequest) throws EmailNotFoundException {
        final String email = passwordResetRequest.getEmail();

        final User user = userService.findByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(email);
        }

        final ResetPasswordToken resetPasswordToken = resetPasswordTokenService.createForUser(user);

        mailService.sendTokenMail("Password reset", "Password reset url: ",
                passwordResetRequest.getPasswordResetUrl(), resetPasswordToken.getToken(), user.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
