package au.com.d2dcrc.ia.search.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The spring JPA repository for users.
 */
@Repository
public interface UserRepositoryJpa extends JpaRepository<User, Long> {

    /**
     * Finds the user by the username principle.
     *
     * @param username the principle of user to find
     * @return the user or null if could not find one
     */
    User findByUsername(final String username);

}
