package au.com.d2dcrc.ia.search.common;

import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.spi.AuthFilter;

/**
 * Filter that is applied to rest assured requests automatically for authentication.
 */
public class JwtAuthFilter implements AuthFilter {

    private final String jwt;

    /**
     * Constructs a new JWT filter with the supplied token to inject to each request header.
     *
     * @param jwt the token to inject
     */
    public JwtAuthFilter(final String jwt) {
        this.jwt = jwt;
    }

    @Override
    public Response filter(
        final FilterableRequestSpecification requestSpec,
        final FilterableResponseSpecification responseSpec,
        final FilterContext filterContext
    ) {
        requestSpec.header("Authorization", "Bearer " + jwt);
        return filterContext.next(requestSpec, responseSpec);
    }
}
