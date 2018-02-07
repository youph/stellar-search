package au.com.d2dcrc.ia.search.management;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import au.com.d2dcrc.ia.search.BaseSpringTest;
import au.com.d2dcrc.ia.search.elastic.ElasticsearchIndexHelper;
import au.com.d2dcrc.ia.search.epg.EpgReferenceFixtures;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import javax.inject.Inject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EpgManagementControllerNoElasticIT extends BaseSpringTest {

    private static final Logger logger = LoggerFactory
        .getLogger(au.com.d2dcrc.ia.search.management.EpgManagementControllerNoElasticIT.class);

    /**
     * Test configuration for this test uses an invalid elasticsearch host.
     */
    @TestConfiguration
    public static class TestConfig {

        /**
         * The elasticsearch client configured to use an invalid port.
         *
         * @return the elasticsearch client
         */
        @Bean
        @Primary
        public RestHighLevelClient elasticHighLevelRestClient() {
            final RestClientBuilder restClientBuilder = RestClient
                .builder(HttpHost.create("localhost:9999"));
            return new RestHighLevelClient(restClientBuilder);
        }
    }

    @Inject
    private ElasticsearchIndexHelper elasticsearchIndexHelper;


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

    @Test
    public void testCreateIndexWithNoElasticServer() {
        final EpgReferenceModel epgReferenceModelWithSchema = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL_GRAPH_SCHEMA;
        createEpgWhenNoElasticServer(epgReferenceModelWithSchema, "imdb");
    }

    private void createEpgWhenNoElasticServer(final EpgReferenceModel epgReferenceModel,
        final String name) {
        final String path = "/api/v1.0/indexes/" + name;

        given()
            .contentType(ContentType.JSON).body(epgReferenceModel)

            .when().post(path)

            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(matchesJsonSchema(getClass().getResource("error-view.schema.json")));
//            .log().body();
    }

    private EpgMetaView[] getAllEpgs() {
        return given()
            .contentType(ContentType.JSON)

            .when()
            .get("/api/v1.0/indexes")

            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value())
            .body(matchesJsonSchema(getClass().getResource("epg-view-list.schema.json")))

            .extract()
            .as(EpgMetaView[].class);
    }
}
