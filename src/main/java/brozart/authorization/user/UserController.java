package brozart.authorization.user;

import brozart.authorization.dto.UserDTO;
import brozart.authorization.dto.VerificationTokenRequest;
import brozart.authorization.exception.EmailAlreadyInUseException;
import brozart.authorization.exception.ExpiredVerificationTokenException;
import brozart.authorization.exception.InvalidVerificationTokenException;
import brozart.authorization.utils.Mapper;
import brozart.authorization.verificationToken.VerificationToken;
import brozart.authorization.verificationToken.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender mailSender;

    @Autowired
    public UserController(final UserService userService, final VerificationTokenService verificationTokenService,
                          final JavaMailSender mailSender) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(final UserDTO userDTO) throws EmailAlreadyInUseException {
        final User registered = userService.registerNewUserAccount(Mapper.map(userDTO));

        final VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(registered);
        verificationToken.updateToken(UUID.randomUUID().toString());
        final VerificationToken savedToken = verificationTokenService.save(verificationToken);

        sendVerificationTokenMail("Registration successful: confirm here: ", userDTO.getVerificationBaseUrl(),
                savedToken.getToken(), registered.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/newVerification")
    public ResponseEntity<?> generateNewVerificationToken(final VerificationTokenRequest request) {
        final VerificationToken verificationToken = verificationTokenService.findByToken(request.getToken());
        verificationToken.updateToken(UUID.randomUUID().toString());
        final VerificationToken savedToken = verificationTokenService.save(verificationToken);

        sendVerificationTokenMail("New registration successful: confirm here: ", request.getVerificationBaseUrl(),
                savedToken.getToken(), verificationToken.getUser().getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmRegistration(final String token) throws Exception {
        // Check if verification token exists
        final VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null) {
            throw new InvalidVerificationTokenException();
        }

        // Check if verification token is not expired
        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        final Date now = cal.getTime();
        if (now.compareTo(verificationToken.getExpiryDate()) >= 0) {
            verificationTokenService.delete(verificationToken);
            throw new ExpiredVerificationTokenException();
        }

        // Enable and save user
        user.setEnabled(true);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void sendVerificationTokenMail(final String message, final String url, final String token, final String recipient) {
        final String confirmationUrl = url + "?token=" + token;
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject("Registration Confirmation");
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom("info.brozartapps@gmail.com");
        mailSender.send(email);
    }
}
