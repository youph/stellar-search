package au.com.d2dcrc.ia.search.security;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * This class encapsulates the generation and validation of JWT tokens.
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Validated
public class JwtProperties {

    @NotEmpty
    private byte[] secret;
    @NotEmpty
    private String issuer;
    @NotNull
    @Positive
    private Long expireSeconds;
    @NotNull
    private String algorithm;

    /**
     * Get the secret key to use. Currently only supporting one symmetric key, though the JWT and the library
     * supports the option of public/private keys (and possibly multiple for key-rotation) - something we may
     * wish to support in the future.
     *
     * @return the binary key data (converted from a base64 encoded string in config)
     */
    public byte[] getSecret() {
        return secret;
    }

    /**
     * Set the secret key to use. Currently only supporting one symmetric key, though the JWT and the library
     * supports the option of public/private keys (and possibly multiple for key-rotation) - something we may
     * wish to support in the future.
     *
     * @param secret the binary key data (converted from a base64 encoded string in config)
     */
    public void setSecret(final byte[] secret) {
        this.secret = secret;
    }

    /**
     * The value for the issuer.
     *
     * <p>See <a href="https://tools.ietf.org/html/rfc7519#section-4.1.1">4.1.1.  "iss" (Issuer) Claim</a>
     *
     * <p>We are mandating this field - not empty
     *
     * @return the issuer
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Set the value issuer.
     *
     * <p>See <a href="https://tools.ietf.org/html/rfc7519#section-4.1.1">4.1.1.  "iss" (Issuer) Claim</a>
     *
     * <p>We are mandating this field - not empty
     *
     * @param issuer the issuer
     */
    public void setIssuer(final String issuer) {
        this.issuer = issuer;
    }

    /**
     * The number of seconds after the issue time which the token should expire.
     *
     * <p>See <a href="https://tools.ietf.org/html/rfc7519#section-4.1.4">'exp' (Expiration Time) Claim</a>
     *
     * <p>We are mandating this field - positive
     *
     * @return the number of seconds after token issued at which to expire
     */
    public Long getExpireSeconds() {
        return expireSeconds;
    }

    /**
     * Set the number of seconds after the issue time which the token should expire.
     *
     * <p>See <a href="https://tools.ietf.org/html/rfc7519#section-4.1.4">'exp' (Expiration Time) Claim</a>
     *
     * <p>We are mandating this field - positive
     *
     * @param expireSeconds the number of seconds after token issued at which to expire
     */
    public void setExpireSeconds(final Long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    /**
     * The Algorithm to use for signing the key.
     *
     * @return the algorithm to use
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Set the Algorithm to use for signing the key.
     *
     * @param algorithm the algorithm to use
     */
    public void setAlgorithm(final String algorithm) {
        this.algorithm = algorithm;
    }

}
