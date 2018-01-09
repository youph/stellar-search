package au.com.d2dcrc.ia.search.security;

import au.com.d2dcrc.ia.search.example.ExampleRestController;
import au.com.d2dcrc.ia.search.user.UserRepositoryJpa;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementation of the spring security {@link UserDetailsManager} and {@link UserDetailsService}.
 *
 * <p>Right now only read-only operations are supported. Account creation of bootstrap user accounts
 * are done via flyway DB migrations. Write operations outside of flyway such as create/edit/delete users and
 * change passwords throw {@link UnsupportedOperationException}.
 */
@Service
public class UserDetailsManagerImpl implements UserDetailsManager {

    private UserRepositoryJpa userRepository;

    /**
     * Constructs a new UserDetailsManager.
     *
     * @param userRepository the JPA repository backing this manager
     */
    public UserDetailsManagerImpl(UserRepositoryJpa userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        au.com.d2dcrc.ia.search.user.User applicationUser = userRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(
            applicationUser.getUsername(),
            applicationUser.getPassword(),
            Collections.singleton(ExampleRestController.CAN_CREATE_TASKS_AUTHORITY)  // TODO: get auths from DB
        );
    }

    @Override
    public void createUser(UserDetails user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
