package brozart.authorization.user;

import brozart.authorization.exception.EmailAlreadyInUseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class.getName());

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find the user " + userName);
        }
        return new CustomUserDetails(user);
    }

    @Transactional
    public User registerNewUserAccount(final User newUser) throws EmailAlreadyInUseException {
        LOG.debug("Registering new user..");
        final User existing = userRepository.findByEmail(newUser.getEmail());
        if (existing != null) {
            LOG.debug("User with email " + newUser.getEmail() + " already registered..");
            throw new EmailAlreadyInUseException();
        }
        newUser.setEnabled(false);
        newUser.setPassword(new BCryptPasswordEncoder(10).encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Transactional
    public void save(final User user) {
        userRepository.save(user);
    }

    public User findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }
}
