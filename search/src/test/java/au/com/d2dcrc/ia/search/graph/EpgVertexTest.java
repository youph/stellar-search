package au.com.d2dcrc.ia.search.graph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests that the {@link EpgVertex} object is correctly serialised into JSON and
 * deserialised from JSON.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class EpgVertexTest {

    private final EpgVertex fixture = new EpgVertex(
        "342D5869102440778B69FDF03756C853",
        "iamanode",
        new EpgProperties("name", "Slow Fred", "IQ", 42),
        Collections.singleton("342D5869102440778B69FDF03756C858")
    );

    private final ClassPathResource jsonResource = new ClassPathResource("vertex.json", getClass());

    @Inject
    private JacksonTester<EpgVertex> json;

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
        EpgVertex vertex = mapper.reader().forType(EpgVertex.class).readValue(json);
        assertEquals(fixture, vertex);
    }

    @Test
    public void testMethods() throws Exception {
        EpgVertex vertex = new EpgVertex();
        vertex.setId("342D5869102440778B69FDF03756C853");
        vertex.setLabel("iamanode");
        vertex.setProperties(new EpgProperties("name", "Slow Fred", "IQ", 42));
        vertex.setGraphIds(Collections.singleton("342D5869102440778B69FDF03756C858"));
        assertEquals(fixture, vertex);
        assertEquals(fixture.getId(), vertex.getId());
        assertEquals(fixture.getLabel(), vertex.getLabel());
        assertEquals(fixture.getProperties(), vertex.getProperties());
        assertEquals(fixture.getGraphIds(), vertex.getGraphIds());
        assertEquals(fixture.hashCode(), vertex.hashCode());
        assertTrue(!vertex.equals(null));
    }

}