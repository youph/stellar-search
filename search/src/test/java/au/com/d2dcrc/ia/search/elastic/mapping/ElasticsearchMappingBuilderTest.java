package au.com.d2dcrc.ia.search.elastic.mapping;

import static org.junit.Assert.assertThat;

import au.com.d2dcrc.ia.search.BaseSpringTest;
import au.com.d2dcrc.ia.search.epg.EpgReferenceFixtures;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import io.restassured.module.jsv.JsonSchemaValidator;
import java.io.IOException;
import javax.inject.Inject;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
@ContextConfiguration(classes = {ElasticsearchMappingBuilder.class})
public class ElasticsearchMappingBuilderTest extends BaseSpringTest {

    @Inject
    private ElasticsearchMappingBuilder mappingBuilder;

    @Test
    public void testCreateElasticsearchMapping() throws IOException, JSONException {
        final String elasticsearchMapping = mappingBuilder.createMapping();

        // Validate generated elasticsearch mapping against test fixture
        final String testFixture = Resources.toString(
            getClass().getResource("imdb-es-mapping-request.json"),
            Charsets.UTF_8);
        JSONAssert.assertEquals(elasticsearchMapping, testFixture, true);

        // Validate generated elasticsearch mapping against json schema
        assertThat(
            elasticsearchMapping,
            JsonSchemaValidator.matchesJsonSchema(
                getClass().getResource("elasticsearch-mapping.schema.json")
            )
        );
    }

    @Test
    public void testCreateElasticsearchMappingFromEpgSchema() throws IOException, JSONException {
        final String elasticsearchMapping = mappingBuilder.createMapping(
            EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL_GRAPH_SCHEMA.getEpgSchema());

        // Validate generated elasticsearch mapping against test fixture
        final String testFixture = Resources.toString(
            getClass().getResource("imdb-graph-schema-es-mapping-request.json"),
            Charsets.UTF_8);
        JSONAssert.assertEquals(elasticsearchMapping, testFixture, true);

        // Validate generated elasticsearch mapping against json schema
        assertThat(
            elasticsearchMapping,
            JsonSchemaValidator.matchesJsonSchema(
                getClass().getResource("elasticsearch-mapping.schema.json")
            )
        );
    }
}
