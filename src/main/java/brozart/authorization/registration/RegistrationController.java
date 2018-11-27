package brozart.authorization.registration;

import brozart.authorization.dto.UserDTO;
import brozart.authorization.dto.VerificationTokenRequest;
import brozart.authorization.exception.EmailAlreadyInUseException;
import brozart.authorization.exception.ExpiredRegistrationToken;
import brozart.authorization.exception.InvalidRegistrationToken;
import brozart.authorization.mail.MailService;
import brozart.authorization.user.User;
import brozart.authorization.user.UserService;
import brozart.authorization.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/user/registration")
public class RegistrationController {

    private final UserService userService;
    private final RegistrationTokenService registrationTokenService;
    private final MailService mailService;

    @Autowired
    public RegistrationController(final UserService userService, final RegistrationTokenService registrationTokenService,
                                  final MailService mailService) {
        this.userService = userService;
        this.registrationTokenService = registrationTokenService;
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity<?> register(final UserDTO userDTO) throws EmailAlreadyInUseException {
        final User registered = userService.registerNewUserAccount(Mapper.map(userDTO));

        final RegistrationToken registrationToken = registrationTokenService.createForUser(registered);

        mailService.sendTokenMail("Registration", "Registration successful: confirm here: ",
                userDTO.getVerificationBaseUrl(), registrationToken.getToken(), registered.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> generateNewVerificationToken(final VerificationTokenRequest request) {
        final RegistrationToken refreshedToken = registrationTokenService.refreshToken(request.getToken());

        mailService.sendTokenMail("Registration", "New registration successful: confirm here: ",
                request.getVerificationBaseUrl(), refreshedToken.getToken(), refreshedToken.getUser().getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmRegistration(final String token) throws Exception {
        // Check if verification token exists
        final RegistrationToken registrationToken = registrationTokenService.findByToken(token);
        if (registrationToken == null) {
            throw new InvalidRegistrationToken();
        }

        // Check if verification token is not expired
        final User user = registrationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        final Date now = cal.getTime();
        if (now.compareTo(registrationToken.getExpiryDate()) >= 0) {
            registrationTokenService.delete(registrationToken);
            throw new ExpiredRegistrationToken();
        }

        // Enable and save user
        user.setEnabled(true);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
