package brozart.authorization.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String username;
    private String password;
    private String email;
    private Boolean enabled;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    void setPassword(final String password) {
        this.password = password;
    }

    String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    Boolean getEnabled() {
        return enabled;
    }

    void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }
}
