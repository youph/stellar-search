package au.com.d2dcrc.ia.search.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.inject.Inject;

/**
 * Configures the authentication cross cutting concern for the API.
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    /**
     * Construct a web security configuration class with the filters and services required to make it work.

     * @param userDetailsService the user details service
     * @param jwtService the JSON web token service for creating and verifying JWTs
     */
    @Inject
    public SecurityConfig(
        final UserDetailsService userDetailsService,
        final JwtService jwtService
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    /**
     * Expose password encoder as a bean so we can encrypt new and updated passwords.
     *
     * @return password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

    /**
     * Configure authentication for the API.
     *
     * @param security a {@link HttpSecurity} builder, used to configure which routes are secured
     */
    @Override
    protected void configure(final HttpSecurity security) throws Exception {

        security
            // we need to disable CSRF because we like POSTing with JSON content, and we use signed tokens.
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()

            // no state kept in application, so disable springs session management
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

            // with no sessions, no caching is required
            .requestCache().requestCache(new NullRequestCache()).and()

            // apply authorisation
            .authorizeRequests()

            // permit anonymous resource requests
            .antMatchers(
                HttpMethod.GET,
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
            ).permitAll()

            // permit swagger and it's associated cruft
            .antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/**",
                "/swagger-ui.html",
                "/webjars/**"
            ).permitAll()

            // permit POSTing to auth for obtaining tokens
            .antMatchers(HttpMethod.POST, "/api/v*/auth").permitAll()

            // everything else, needs the token
            .antMatchers("/api/**").authenticated().and()

            .addFilterAt(new AuthenticationFilter(jwtService), BasicAuthenticationFilter.class)

            // Corz
            .cors().and()

            // disable page caching
            .headers().cacheControl();

    }

    @Override
    public void configure(
        final AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(this.userDetailsService)
            .passwordEncoder(this.passwordEncoder);
    }


    /**
     * Build a configured CORs source, that allows our UI to POST body-content at our API, among other things.
     *
     * @return a configured CORS source.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);

        // TODO - bean under a dev profile or external configuration via profile
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedOrigin("http://127.0.0.1:4200");

        // TODO - bean under a local integration test mode configuration via profile
        configuration.addAllowedOrigin("http://localhost:49152");
        configuration.addAllowedOrigin("http://127.0.0.1:49152");

        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);

        return source;
    }

}
