package au.com.d2dcrc.ia.search.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * Manages issuing and decoding JSON Web Tokens (JWTs). This must be thread safe.
 */
@Component
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    /**
     * List of private claim names. Public or registered claims should first be consulted at
     * https://www.iana.org/assignments/jwt/jwt.xhtml
     * before defining a private claim
     */
    public static class ClaimNames {

        /**
         * Claim for list of {@link GrantedAuthority} names associated with the
         * claim subject (username principle).
         */
        public static final String GRANTED_AUTHORITIES = "aty";
    }

    private final JwtProperties jwtProperties;

    /**
     * Constructor.
     *
     * @param jwtProperties the properties for the service
     */
    @Inject
    public JwtService(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * Issue a new token, valid from the current time.
     *
     * @param credentials the authenticated credentials to issue the token for
     * @return The encoded JWT string
     */
    public String issueToken(final Authentication credentials) {

        logger.info("Generating token for principle '{}'", credentials.getName());

        final Instant createdTime = Instant.now();
        final Instant expiryTime = createdTime.plusSeconds(this.jwtProperties.getExpireSeconds());

        // Claim the granted authorities, which can be converted into a domain privilege (if required)
        final List<String> authorities = credentials.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return Jwts.builder()
            .setIssuedAt(Date.from(createdTime))
            .setExpiration(Date.from(expiryTime))
            .setNotBefore(Date.from(createdTime))
            .setIssuer(jwtProperties.getIssuer())
            .setSubject(credentials.getName())
            .claim(ClaimNames.GRANTED_AUTHORITIES, authorities)
            .signWith(
                SignatureAlgorithm.forName(jwtProperties.getAlgorithm()),
                jwtProperties.getSecret()
            )
            .compact();

    }

    /**
     * Decodes a JWT, verifies/validates signature and returns an PreAuthenticatedAuthenticationToken containing
     * a principle and collection of authorities. No credentials are included, since we are pre-authenticated
     * provided the token and signature is valid.
     *
     * @param token the token to decode/validate
     * @return a pre authentication token
     * @throws JwtException on any JWT error
     */
    public PreAuthenticatedAuthenticationToken decodeToken(final String token) {

        final Claims claims = Jwts.parser()
            .requireIssuer(jwtProperties.getIssuer())
            .setSigningKey(jwtProperties.getSecret())
            .parseClaimsJws(token)
            .getBody();

        // shitty API - I'm contemplating switching to https://github.com/auth0/java-jwt
        @SuppressWarnings("unchecked")
        final List<String> authsStrings = claims.get(ClaimNames.GRANTED_AUTHORITIES, List.class);

        final List<GrantedAuthority> auths = authsStrings.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        return new PreAuthenticatedAuthenticationToken(claims.getSubject(), null, auths);
    }

}
