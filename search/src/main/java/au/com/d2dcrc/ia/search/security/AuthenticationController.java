package au.com.d2dcrc.ia.search.security;

import au.com.d2dcrc.ia.search.status.ErrorView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * End point for creating and refreshing authentication tokens.
 */
@Api(description = "Manage authentication tokens")
@RestController
@ApiResponses({
    @ApiResponse(
        code = org.apache.http.HttpStatus.SC_FORBIDDEN, 
        message = "Accessing the requested resource is forbidden"
    ),
    @ApiResponse(
        code = org.apache.http.HttpStatus.SC_NOT_FOUND, 
        message = "The requested resource was not found"
    )
})
@RequestMapping(
    value = "/api/v1/auth",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationController(
        final AuthenticationManager authenticationManager,
        final JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Create a new jwt token for the credentials supplied (if they are valid).
     *
     * @param credentials the credentials supplied by the user
     * @return a {@link JwtView} for the token if valid credentials were supplied
     */
    @ApiOperation("Post credentials. Get token. #Profit.")
    @PostMapping("")
    @ApiResponses({
        @ApiResponse(
            code = org.apache.http.HttpStatus.SC_CREATED,
            message = "Successfully created an authentication token"
        ),
        @ApiResponse(
            code = org.apache.http.HttpStatus.SC_UNAUTHORIZED,
            message = "Authentication failed with supplied credentials",
            response = ErrorView.class
        )
    })
    @ResponseStatus(HttpStatus.CREATED)
    public JwtView createAuthenticationToken(@RequestBody final CredentialModel credentials) {

        final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
            credentials.getUsername().trim(),
            credentials.getPassword()
        );

        final Authentication auth = authenticationManager.authenticate(authRequest);


        final String token = jwtService.issueToken(auth);

        SecurityContextHolder.getContext().setAuthentication(auth);

        return new JwtView(token);

    }

}
