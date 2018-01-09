package au.com.d2dcrc.ia.search.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Basic user entity for persistence.
 */
@Entity
public class User {

    @Id
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public User() {
    }

    /**
     * Gets the principle of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the principle of the user.
     *
     * @param username the username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets the encoded password of the user as according to {@link PasswordEncoder} bean.
     * This should be {@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder}
     *
     * @return the encoded password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the encoded password of the user. This should NEVER be plain text.
     *
     * @param password the encoded password
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
