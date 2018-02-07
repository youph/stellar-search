package au.com.d2dcrc.ia.search.epg;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.d2dcrc.ia.search.BaseSpringTest;
import au.com.d2dcrc.ia.search.management.EpgReferenceModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

/**
 * Tests the serialisation and deserialisation of the {@link EpgReferenceModel}.
 */
@JsonTest
public class EpgReferenceModelTest extends BaseSpringTest {

    private final String fixtureJsonString = EpgReferenceFixtures.IMDB_EPG_REFERENCE_JSON;
    private final EpgReferenceModel fixtureObject = EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL;

    @Inject
    private JacksonTester<EpgReferenceModel> json;

    @Test
    public void testSerialize() throws Exception {
        assertThat(this.json.write(this.fixtureObject)).isEqualToJson(this.fixtureJsonString);
        assertThat(this.json.write(EpgReferenceFixtures.IMDB_EPG_REFERENCE_MODEL_GRAPH_SCHEMA))
            .isEqualToJson(EpgReferenceFixtures.IMDB_EPG_REFERENCE_GRAPH_SCHEMA_JSON);
    }

    @Test
    public void testDeserialize() throws Exception {
        assertThat(this.json.parseObject(this.fixtureJsonString)).isEqualTo(this.fixtureObject);
        assertThat(this.json.parse(this.fixtureJsonString)).isEqualTo(this.fixtureObject);
    }

}
