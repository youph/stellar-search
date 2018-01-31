package au.com.d2dcrc.ia.search.management;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertArrayEquals;
import au.com.d2dcrc.ia.search.BaseSpringTest;
import au.com.d2dcrc.ia.search.epg.EpgReferenceFixtures;
import java.net.MalformedURLException;
import java.net.URL;
import javax.inject.Inject;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

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
    public void testCreateIndex() {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        assertThat(getAllEpgs(), arrayWithSize(0));

        createEpgWhenNotExists(epgReferenceModel, "imdb");
        assertThat(getAllEpgs(), arrayWithSize(1));

        createEpgWhenDoesExist(epgReferenceModel, "imdb");
        assertThat(getAllEpgs(), arrayWithSize(1));
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

        deleteEpgWhenNotExists("imdb");
        assertThat(getAllEpgs(), arrayWithSize(0));
    }

    @Test
    public void testGetAllEpgIndex() {
        final EpgReferenceModel epgReferenceModel = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;
        final EpgMetaView view1 = createEpgWhenNotExists(epgReferenceModel, "imdb");
        assertThat(getAllEpgs(), arrayWithSize(1));
        assertArrayEquals(getAllEpgs(), new EpgMetaView[] { view1 });

        final EpgMetaView view2 = createEpgWhenNotExists(epgReferenceModel, "imdb2");
        assertThat(getAllEpgs(), arrayWithSize(2));
        assertArrayEquals(getAllEpgs(), new EpgMetaView[] { view1, view2 });

        final EpgMetaView view3 = createEpgWhenNotExists(epgReferenceModel, "imdb3");
        assertThat(getAllEpgs(), arrayWithSize(3));
        assertArrayEquals(getAllEpgs(), new EpgMetaView[] { view1, view2, view3 });
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

    private void createEpgWhenDoesExist(final EpgReferenceModel epgReferenceModel, final String name) {
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

}
