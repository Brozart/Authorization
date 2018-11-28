package brozart.authorization.registration;

import brozart.authorization.dto.RegisterTokenRequest;
import brozart.authorization.dto.UserDTO;
import brozart.authorization.exception.EmailAlreadyInUseException;
import brozart.authorization.exception.ExpiredRegistrationTokenException;
import brozart.authorization.exception.InvalidRegistrationTokenException;
import brozart.authorization.mail.MailService;
import brozart.authorization.user.User;
import brozart.authorization.user.UserService;
import brozart.authorization.utils.Mapper;
import brozart.authorization.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> generateNewRegisterToken(final RegisterTokenRequest request) {
        final RegistrationToken refreshedToken = registrationTokenService.refreshToken(request.getToken());

        mailService.sendTokenMail("Registration", "New registration successful: confirm here: ",
                request.getRegistrationBaseUrl(), refreshedToken.getToken(), refreshedToken.getUser().getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmRegistration(final String token) throws Exception {
        // Check if verification token exists
        final RegistrationToken registrationToken = registrationTokenService.findByToken(token);
        if (registrationToken == null) {
            throw new InvalidRegistrationTokenException();
        }

        // Check if verification token is not expired
        if (TokenUtils.isTokenExpired(registrationToken.getExpiryDate())) {
            throw new ExpiredRegistrationTokenException();
        }

        // Enable and save user
        final User user = registrationToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        registrationTokenService.delete(registrationToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
