package au.com.d2dcrc.ia.search.query;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.hasItem;

import au.com.d2dcrc.ia.search.BaseSpringTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import java.io.IOException;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueryRestControllerIT extends BaseSpringTest {

    private static final Logger logger = LoggerFactory.getLogger(QueryRestControllerIT.class);

    @Inject
    private ObjectMapper mapper;

    /**
     * Inject the ephemeral port used for integration testing into
     *
     * @param port the ephemeral port used
     */
    @LocalServerPort
    public void setPort(int port) {
        logger.debug("Setting port for rest assured to {}", port);
        RestAssured.port = port;
    }

    /**
     * Sets the config to log the request/response on validation failure.
     */
    @BeforeClass
    public static void beforeClass() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
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
            .readValue(getClass().getResource("user-query-01.json"), QueryRequestModel.class);
        view = search(queryModel);
        Assert.assertEquals(view.getTotal(), 3);

        queryModel = mapper
            .readValue(getClass().getResource("user-query-02.json"), QueryRequestModel.class);
        view = search(queryModel);
        Assert.assertEquals(view.getTotal(), 3);

        queryModel = mapper
            .readValue(getClass().getResource("user-query-04.json"), QueryRequestModel.class);
        view = search(queryModel);
        Assert.assertEquals(view.getTotal(), 3);

        queryModel = mapper
            .readValue(getClass().getResource("user-query-05.json"), QueryRequestModel.class);
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

}
