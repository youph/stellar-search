package au.com.d2dcrc.ia.search.management;

import au.com.d2dcrc.ia.search.BaseSpringTest;
import au.com.d2dcrc.ia.search.epg.EpgReferenceFixtures;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;

/**
 * Integration test for the EPG Management Controller.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EpgManagementControllerIT extends BaseSpringTest {

    private static final Logger logger = LoggerFactory.getLogger(EpgManagementControllerIT.class);

    @Inject
    private EpgMetaRepository epgMetaRepository;

    /**
     * Inject the ephemeral port used for integration testing into
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
     * thread and servlet container. Thus the default rollbacks do not apply outside the
     * test's context. See subnote of
     * <a href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications">41.3 Testing Spring Boot applications</a>
     *
     * <p>If you can figure another way - please proOve me wrong :) .
     */
    @After
    public void after() {
        epgMetaRepository.deleteAll();
    }

    @Test
    public void testListEmptyIndexes() throws Exception {
        final EpgMetaView[] epgMetaViewGetAll = getAllEpgs();
        assertThat(epgMetaViewGetAll, arrayWithSize(0));
    }

    @Test
    public void testCreateIndex() {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        createEpg(epgReferenceModel, "imdb");
    }


    @Test
    public void testGetEpgIndex() {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        final EpgMetaView epgMetaViewCreated = createEpg(epgReferenceModel, "imdb");
        final EpgMetaView epgMetaViewGetByName = getEpg("imdb");
        assertThat(epgMetaViewGetByName, is(epgMetaViewCreated));
    }

    @Test
    public void testGetAllEpgIndex() {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        final EpgMetaView epgMetaViewCreated = createEpg(epgReferenceModel, "imdb");
        final EpgMetaView[] epgMetaViewGetAll = getAllEpgs();
        assertThat(epgMetaViewGetAll, arrayWithSize(1));
        assertThat(epgMetaViewGetAll[0], is(epgMetaViewCreated));
    }

    @Test
    public void testDeleteEpgIndex() {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        createEpg(epgReferenceModel, "imdb");
        deleteEpg("imdb");
    }

    private EpgMetaView createEpg(
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

    private void deleteEpg(final String name) {
        given()
            .contentType(ContentType.JSON)

            .when()
            .delete("/api/v1.0/indexes/" + name)

            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
