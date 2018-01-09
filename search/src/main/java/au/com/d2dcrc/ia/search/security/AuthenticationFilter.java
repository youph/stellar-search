package au.com.d2dcrc.ia.search.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter for extracting the JWT from the Authorization header if any,
 * verifying the signature, and setting the current security context's authorization credentials.
 */
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_SCHEME = "Bearer";

    private final JwtService jwtService;

    /**
     * Constructor authentication filter.
     *
     * @param jwtService the JWT service needed for decoding and verifying signature
     */
    public AuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain chain
    ) throws IOException, ServletException {

        final String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(AUTH_SCHEME + " ")) {

            final String token = header.substring(AUTH_SCHEME.length() + 1);

            final AbstractAuthenticationToken authentication = jwtService.decodeToken(token);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(req, res);

    }


}
