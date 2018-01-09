package au.com.d2dcrc.ia.search.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A model that encapsulates the login credentials provided by user of the API.
 */
@ApiModel
public class CredentialModel {

    @ApiModelProperty(value = "username", required = true, example = "im-a-user")
    private final String username;

    @ApiModelProperty(value = "password", required = true, example = "im-not-a-good-password")
    private final String password;

    /**
     * Constructor for the credential model.
     *
     * @param username the username principle for authentication
     * @param password the plain text password
     */
    public CredentialModel(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username principle.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the plain text password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

}
