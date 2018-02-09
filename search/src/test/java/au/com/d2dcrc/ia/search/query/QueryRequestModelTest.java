package au.com.d2dcrc.ia.search.query;

import au.com.d2dcrc.ia.search.BaseSpringTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import javax.inject.Inject;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
@ContextConfiguration(classes = {ElasticsearchQueryBuilder.class})
public class QueryRequestModelTest extends BaseSpringTest {

    private static Logger logger = LoggerFactory.getLogger(QueryRequestModel.class);

    @Inject
    private ObjectMapper mapper;

    @Inject
    private ElasticsearchQueryBuilder queryBuilder;

    @Test
    public void testCreateElasticQuery01() throws IOException, JSONException {
        final URL jsonResource = getClass().getResource("user-query-01.json");
        final String testFixture = queryBuilder.toElasticsearchQueryString(
            mapper.readValue(jsonResource, QueryRequestModel.class)
        );
        final String expectedQuery = Resources.toString(
            getClass().getResource("es-query-01.json"),
            Charsets.UTF_8
        );

        // debug
        logger.debug(Resources.toString(jsonResource, Charsets.UTF_8));
        logger.debug(testFixture);
        logger.debug(expectedQuery);

        JSONAssert.assertEquals(testFixture, expectedQuery, true);
    }

    @Test
    public void testCreateElasticQuery02() throws IOException, JSONException {
        final URL jsonResource = getClass().getResource("user-query-02.json");
        final String testFixture = queryBuilder.toElasticsearchQueryString(
            mapper.readValue(jsonResource, QueryRequestModel.class)
        );
        final String expectedQuery = Resources.toString(
            getClass().getResource("es-query-02.json"),
            Charsets.UTF_8
        );

        // debug
        logger.debug(Resources.toString(jsonResource, Charsets.UTF_8));
        logger.debug(testFixture);
        logger.debug(expectedQuery);

        JSONAssert.assertEquals(testFixture, expectedQuery, true);
    }

    @Test
    public void testCreateElasticQuery03() throws IOException, JSONException {
        final URL jsonResource = getClass().getResource("user-query-03.json");
        final String testFixture = queryBuilder.toElasticsearchQueryString(
            mapper.readValue(jsonResource, QueryRequestModel.class)
        );
        final String expectedQuery = Resources.toString(
            getClass().getResource("es-query-03.json"),
            Charsets.UTF_8
        );

        // debug
        logger.debug(Resources.toString(jsonResource, Charsets.UTF_8));
        logger.debug(testFixture);
        logger.debug(expectedQuery);

        JSONAssert.assertEquals(testFixture, expectedQuery, true);
    }

    @Test
    public void testCreateElasticQuery04() throws IOException, JSONException {
        final URL jsonResource = getClass().getResource("user-query-04.json");
        final String testFixture = queryBuilder.toElasticsearchQueryString(
            mapper.readValue(jsonResource, QueryRequestModel.class)
        );
        final String expectedQuery = Resources.toString(
            getClass().getResource("es-query-04.json"),
            Charsets.UTF_8
        );

        // debug
        logger.debug(Resources.toString(jsonResource, Charsets.UTF_8));
        logger.debug(testFixture);
        logger.debug(expectedQuery);

        JSONAssert.assertEquals(testFixture, expectedQuery, true);
    }

    @Test
    public void testCreateElasticQuery05() throws IOException, JSONException {
        final URL jsonResource = getClass().getResource("user-query-05.json");
        final String testFixture = queryBuilder.toElasticsearchQueryString(
            mapper.readValue(jsonResource, QueryRequestModel.class)
        );
        final String expectedQuery = Resources.toString(
            getClass().getResource("es-query-05.json"),
            Charsets.UTF_8
        );

        // debug
        logger.debug(Resources.toString(jsonResource, Charsets.UTF_8));
        logger.debug(testFixture);
        logger.debug(expectedQuery);

        JSONAssert.assertEquals(testFixture, expectedQuery, true);
    }
}
