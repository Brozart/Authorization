package brozart.authorization.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds the user by given user name.
     *
     * @param userName the user name
     * @return the found user
     */
    User findByUsername(String userName);

    /**
     * Finds the user by given email.
     *
     * @param email the email
     * @return the found user
     */
    User findByEmail(String email);
}
