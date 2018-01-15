package au.com.d2dcrc.ia.search.security;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Encapsulates a jwt token.
 */
@ApiModel
public class JwtView {

    @ApiModelProperty(required = true)
    private final String jwt;

    /**
     * Construct a new jwt token view.
     *
     * @param jwt the token to encapsulate
     */
    public JwtView(@JsonProperty("jwt") final String jwt) {
        this.jwt = jwt;
    }

    /**
     * Gets the JSON Web Token.
     *
     * @return the JWT
     */
    public String getJwt() {
        return jwt;
    }

}
