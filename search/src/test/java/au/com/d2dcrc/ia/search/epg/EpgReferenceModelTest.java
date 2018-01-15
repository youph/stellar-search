package au.com.d2dcrc.ia.search.epg;

import au.com.d2dcrc.ia.search.BaseSpringTest;
import au.com.d2dcrc.ia.search.management.EpgReferenceModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.net.URISyntaxException;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

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
    }

    @Test
    public void testDeserialize() throws Exception {
        assertThat(this.json.parseObject(this.fixtureJsonString)).isEqualTo(this.fixtureObject);
        assertThat(this.json.parse(this.fixtureJsonString)).isEqualTo(this.fixtureObject);
    }

}
