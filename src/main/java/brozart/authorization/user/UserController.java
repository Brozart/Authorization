package brozart.authorization.user;

import brozart.authorization.exception.EmailExistsException;
import brozart.authorization.verificationToken.VerificationToken;
import brozart.authorization.verificationToken.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender mailSender;

    @Value("${my.url}")
    private String url;

    @Autowired
    public UserController(final UserService userService, final VerificationTokenService verificationTokenService,
                          final JavaMailSender mailSender) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(final User user) throws EmailExistsException {
        final User registered = userService.registerNewUserAccount(user);
        final VerificationToken verificationToken = verificationTokenService.create(registered);
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";

        final String confirmationUrl = url + "/registrationConfirm?token=" +
                verificationToken.getToken();
        final String message = "Registration successful: confirm here: ";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom("info.brozartapps@gmail.com");
        mailSender.send(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
