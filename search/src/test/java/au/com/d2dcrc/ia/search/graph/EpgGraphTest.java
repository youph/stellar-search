package au.com.d2dcrc.ia.search.graph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class EpgGraphTest {

    private final EpgGraph fixture = new EpgGraph(
        "342D5869102440778B69FDF03756C858",
        "iamagraph",
        new EpgProperties("key1", "value1", "key2", 42, "key3", true)
    );

    private final ClassPathResource jsonResource = new ClassPathResource("graph.json", getClass());

    @Inject
    private JacksonTester<EpgGraph> json;

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
    public void testSerializeDeserialize() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fixture);
        EpgGraph graph = mapper.reader().forType(EpgGraph.class).readValue(json);
        assertEquals(fixture, graph);
    }

    @Test
    public void testMethods() throws Exception {
        EpgGraph graph = new EpgGraph();
        graph.setId("342D5869102440778B69FDF03756C858");
        graph.setLabel("iamagraph");
        graph.setProperties(new EpgProperties("key1", "value1", "key2", 42, "key3", true));
        assertEquals(fixture, graph);
        assertEquals(fixture.getId(), graph.getId());
        assertEquals(fixture.getLabel(), graph.getLabel());
        assertEquals(fixture.getProperties(), graph.getProperties());
        assertEquals(fixture.hashCode(), graph.hashCode());
        assertTrue(!graph.equals(null));
    }

}