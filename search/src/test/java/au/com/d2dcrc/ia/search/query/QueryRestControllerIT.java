package au.com.d2dcrc.ia.search.query;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import au.com.d2dcrc.ia.search.BaseSpringTest;
import au.com.d2dcrc.ia.search.common.JwtAuthFilter;
import au.com.d2dcrc.ia.search.security.CredentialModel;
import au.com.d2dcrc.ia.search.security.JwtView;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import java.io.IOException;
import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueryRestControllerIT extends BaseSpringTest {

    private static final Logger logger = LoggerFactory.getLogger(QueryRestControllerIT.class);

    @Inject
    ObjectMapper mapper;

    /**
     * Sets the authentication filter before each test.
     *
     * <p>Authentication can be disabled by `given().auth().none()`
     */
    @Before
    public void before() {
        // create a new JWT auth filter (with new token) before each test
        // this needs to be done after setting the port
        RestAssured.replaceFiltersWith(auth());
    }

    /**
     * Tests the search endpoint. All valid requests currently return the same mock response
     * containing 3 hits.
     */
    @Test
    public void testSearchValidQuery() throws IOException {
        QueryRequestModel queryModel = null;
        QueryResultView view = null;

        queryModel = mapper
            .readValue(getClass().getResource("query001.json"), QueryRequestModel.class);
        view = search(queryModel);
        Assert.assertEquals(view.getTotal(), 3);

        queryModel = mapper
            .readValue(getClass().getResource("query002.json"), QueryRequestModel.class);
        view = search(queryModel);
        Assert.assertEquals(view.getTotal(), 3);

        queryModel = mapper
            .readValue(getClass().getResource("query004.json"), QueryRequestModel.class);
        view = search(queryModel);
        Assert.assertEquals(view.getTotal(), 3);

        queryModel = mapper
            .readValue(getClass().getResource("query005.json"), QueryRequestModel.class);
        view = search(queryModel);
        Assert.assertEquals(view.getTotal(), 3);
    }

    /**
     * Tests the search endpoint with invalid json requests.
     */
    @Test
    public void testSearchInvalidQuery() {
        /**
         * Empty query request body.
         */
        final String QUERY_NULL_NODE = "{}";
        search(QUERY_NULL_NODE).assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.objectName", hasItem("queryRequestModel"))
            .body("errors.field", hasItem("node"))
            .body("errors.code", hasItem("NotNull"));

        /**
         * Query with no node label (required).
         */
        final String QUERY_NULL_NODE_LABEL = "{ \"node\": { } }";
        search(QUERY_NULL_NODE_LABEL).assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("errors.objectName", hasItem("queryRequestModel"))
            .body("errors.field", hasItem("node.label"))
            .body("errors.code", hasItem("NotNull"));
    }

    private QueryResultView search(QueryRequestModel body) {
        return given()
            .contentType(ContentType.JSON)
            .body(body)

            .when()
            .get("/api/v1.0/search")

            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value())
            /**
             * Ensure the response matches the json schema.
             */
            .body(matchesJsonSchema(getClass().getResource("query-view.schema.json")))

            .extract()
            .as(QueryResultView.class);
    }

    private ValidatableResponse search(String body) {
        return given()
            .contentType(ContentType.JSON)
            .body(body)

            .when()
            .get("/api/v1.0/search")

            .then()
            .contentType(ContentType.JSON);
    }

    private static JwtAuthFilter auth() {

        final CredentialModel creds = new CredentialModel(
            "test-user",
            "only-for-development"
        );

        final JwtView jwtView = given()
            .contentType(ContentType.JSON)
            .body(creds)

            .when()
            .post("/api/v1.0/auth")

            .then()
            .statusCode(HttpStatus.CREATED.value())
            .contentType(ContentType.JSON)
            .extract()
            .as(JwtView.class);

        return new JwtAuthFilter(jwtView.getJwt());
    }
}
