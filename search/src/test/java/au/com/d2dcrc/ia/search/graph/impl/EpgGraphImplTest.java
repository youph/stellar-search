package au.com.d2dcrc.ia.search.graph.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import au.com.d2dcrc.ia.search.graph.EpgGraph;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests that the {@link EpgGraph} object is correctly serialised into JSON and
 * deserialised from JSON.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class EpgGraphImplTest {

    private final EpgGraphImpl fixture = new EpgGraphImpl(
        "342D5869102440778B69FDF03756C858",
        "iamagraph",
        new EpgPropertiesImpl("key1", "value1", "key2", 42, "key3", true)
    );

    private final ClassPathResource jsonResource = new ClassPathResource("graph.json", getClass());

    @Inject
    private JacksonTester<EpgGraphImpl> json;

    @Test
    public void testSerialize() throws Exception {
        assertThat(this.json.write(this.fixture)).isEqualToJson(this.jsonResource.getPath());
    }

    @Test
    public void testDeserialize() throws Exception {
        assertThat(this.json.read(this.jsonResource)).isEqualTo(this.fixture);
        assertThat(this.json.readObject(this.jsonResource)).isEqualTo(this.fixture);
    }

    @Test
    public void testSerializeDeserailize() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fixture);
        EpgGraph graph1 = mapper.reader().forType(EpgGraphImpl.class).readValue(json);
        assertEquals(fixture, graph1);
        SimpleModule mod = new SimpleModule();
        mod.addDeserializer(EpgGraph.class, new EpgGraphImplDeserializer());
        mapper.registerModule(mod);
        EpgGraph graph2 = mapper.reader().forType(EpgGraph.class).readValue(json);
        assertEquals(fixture, graph2);
    }

}