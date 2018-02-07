package au.com.d2dcrc.ia.search.management;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import au.com.d2dcrc.ia.search.BaseSpringTest;
import au.com.d2dcrc.ia.search.elastic.ElasticsearchIndexHelper;
import au.com.d2dcrc.ia.search.epg.EpgReferenceFixtures;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import javax.inject.Inject;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.JSONException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

/**
 * Integration test for the EPG Management Controller.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EpgManagementControllerIT extends BaseSpringTest {

    private static final Logger logger = LoggerFactory.getLogger(EpgManagementControllerIT.class);

    @Inject
    private EpgMetaRepository epgMetaRepository;

    @Inject
    private ElasticsearchIndexHelper elasticsearchIndexHelper;

    @Inject
    private ObjectMapper mapper;

    @Inject
    private RestHighLevelClient restHighLevelClient;

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
     * Clears the database after each test.
     *
     * <p>If you think you can do this with an @Transaction Annotation, you can't. Despite having a
     * correctly configured transaction manager, the commits to the database happen in a different
     * thread and servlet container. Thus the default rollbacks do not apply outside the test's
     * context. See subnote of <a href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications">41.3
     * Testing Spring Boot applications</a>
     *
     * <p>If you can figure another way - please proOve me wrong :) .
     */
    @After
    public void after() {
        epgMetaRepository.deleteAll();
        elasticsearchIndexHelper.deleteAllElasticIndices();
    }

    @Test
    public void testCreateIndex() throws JSONException, IOException {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        assertThat(getAllEpgs(), arrayWithSize(0));

        createEpgWhenNotExists(epgReferenceModel, "imdb");
        assertThat(getAllEpgs(), arrayWithSize(1));

        // Validate the elasticsearch mapping (response) against the test fixture
        final String elasticsearchMappingResponse = getElasticsearchIndexMapping("imdb");
        final String testFixture = Resources.toString(
            getClass().getResource("imdb-es-mapping-response.json"), Charsets.UTF_8);

        JSONAssert.assertEquals(elasticsearchMappingResponse, testFixture, false);

        createEpgWhenDoesExist(epgReferenceModel, "imdb");
        assertThat(getAllEpgs(), arrayWithSize(1));
    }

    @Test
    public void testCreateIndexWithGraphSchema() throws JSONException, IOException {
        final EpgReferenceModel epgReferenceModelWithSchema = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL_GRAPH_SCHEMA;
        createEpgWhenNotExists(epgReferenceModelWithSchema, "imdb");
        assertThat(getAllEpgs(), arrayWithSize(1));

        final String elasticsearchMappingResponse = getElasticsearchIndexMapping("imdb");
        final String testFixture = Resources.toString(
            getClass().getResource("imdb-graph-schema-es-mapping-response.json"), Charsets.UTF_8);

        JSONAssert.assertEquals(elasticsearchMappingResponse, testFixture, false);
    }

    @Test
    public void testGetEpgIndex() {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        final EpgMetaView epgMetaViewCreated = createEpgWhenNotExists(epgReferenceModel, "imdb");
        final EpgMetaView epgMetaViewGetByName = getEpg("imdb");
        assertThat(epgMetaViewGetByName, is(epgMetaViewCreated));
    }

    @Test
    public void testDeleteEpgIndex() {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        createEpgWhenNotExists(epgReferenceModel, "imdb");

        deleteEpgWhenDoesExist("imdb");
        assertThat(getAllEpgs(), arrayWithSize(0));

        assertNull(getElasticsearchIndexMapping("imdb"));

        deleteEpgWhenNotExists("imdb");
        assertThat(getAllEpgs(), arrayWithSize(0));
    }

    @Test
    public void testGetAllEpgIndex() {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        final EpgMetaView view1 = createEpgWhenNotExists(epgReferenceModel, "imdb");
        assertThat(getAllEpgs(), arrayWithSize(1));
        assertArrayEquals(getAllEpgs(), new EpgMetaView[]{view1});

        final EpgMetaView view2 = createEpgWhenNotExists(epgReferenceModel, "imdb2");
        assertThat(getAllEpgs(), arrayWithSize(2));
        assertArrayEquals(getAllEpgs(), new EpgMetaView[]{view1, view2});

        final EpgMetaView view3 = createEpgWhenNotExists(epgReferenceModel, "imdb3");
        assertThat(getAllEpgs(), arrayWithSize(3));
        assertArrayEquals(getAllEpgs(), new EpgMetaView[]{view1, view2, view3});

        assertThat(elasticsearchIndexHelper.getElasticIndices(),
            hasItems("imdb", "imdb2", "imdb3"));
    }

    private EpgMetaView createEpgWhenNotExists(
        final EpgReferenceModel epgReferenceModel,
        final String name
    ) {

        final String path = "/api/v1.0/indexes/" + name;

        final EpgMetaView view = given()
            .contentType(ContentType.JSON)
            .body(epgReferenceModel)

            .when()
            .post(path)

            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.CREATED.value())
            .header(
                "Location",
                url -> {
                    try {
                        return new URL(url).getPath();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                },
                is(path)
            ).body(matchesJsonSchema(getClass().getResource("epg-view.schema.json")))
            .body("name", is(name))

            .extract()
            .as(EpgMetaView.class);

        assertThat(view.getCreatedFrom().getGraphs(), is(epgReferenceModel.getGraphs()));
        assertThat(view.getCreatedFrom().getVertices(), is(epgReferenceModel.getVertices()));
        assertThat(view.getCreatedFrom().getEdges(), is(epgReferenceModel.getEdges()));

        return view;
    }

    private void createEpgWhenDoesExist(final EpgReferenceModel epgReferenceModel,
        final String name) {
        final String path = "/api/v1.0/indexes/" + name;

        given()
            .contentType(ContentType.JSON).body(epgReferenceModel)

            .when().post(path)

            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(matchesJsonSchema(getClass().getResource("error-view.schema.json")));
    }

    private EpgMetaView getEpg(final String name) {
        return given()
            .contentType(ContentType.JSON)

            .when()
            .get("/api/v1.0/indexes/" + name)

            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value())
            .body(matchesJsonSchema(getClass().getResource("epg-view.schema.json")))

            .extract()
            .as(EpgMetaView.class);
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

    private void deleteEpgWhenDoesExist(final String name) {
        given()
            .contentType(ContentType.JSON)

            .when()
            .delete("/api/v1.0/indexes/" + name)

            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void deleteEpgWhenNotExists(final String name) {
        given()
            .contentType(ContentType.JSON)

            .when()
            .delete("/api/v1.0/indexes/" + name)

            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(matchesJsonSchema(getClass().getResource("error-view.schema.json")));
    }

    /**
     * Get the mapping for a specified index from elasticsearch.
     *
     * @param indexName the specified index
     * @return the mapping as a string or null if it doesn't exist
     */
    private String getElasticsearchIndexMapping(String indexName) {
        try {
            final Response response = restHighLevelClient.getLowLevelClient()
                .performRequest(
                    HttpMethod.GET.toString(), "/" + indexName + "/_mapping",
                    Collections.emptyMap());
            final String entity = EntityUtils.toString(response.getEntity());
            final JsonNode node = mapper.readTree(entity);

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (ResponseException e) {
            if (e.getResponse().getStatusLine().getStatusCode() == HttpStatus.NOT_FOUND.value()) {
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }
}
