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
 * Tests that the {@link EpgEdge} object is correctly serialised into JSON and
 * deserialised from JSON.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class EpgEdgeTest {

    private final EpgEdge fixture = new EpgEdge(
        "342D5869102440778B69FDF03756C850",
        "isRelatedTo",
        new EpgProperties("relationship", "sibling"),
        "342D5869102440778B69FDF03756C853",
        "342D5869102440778B69FDF03756C851",
        Collections.singleton("342D5869102440778B69FDF03756C858")
    );

    private final ClassPathResource jsonResource = new ClassPathResource("edge.json", getClass());

    @Inject
    private JacksonTester<EpgEdge> json;

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
        EpgEdge edge = mapper.reader().forType(EpgEdge.class).readValue(json);
        assertEquals(fixture, edge);
    }

    @Test
    public void testMethods() throws Exception {
        EpgEdge edge = new EpgEdge();
        edge.setId("342D5869102440778B69FDF03756C850");
        edge.setLabel("isRelatedTo");
        edge.setProperties(new EpgProperties("relationship", "sibling"));
        edge.setSourceId("342D5869102440778B69FDF03756C853");
        edge.setTargetId("342D5869102440778B69FDF03756C851");
        edge.setGraphIds(Collections.singleton("342D5869102440778B69FDF03756C858"));
        assertEquals(fixture, edge);
        assertEquals(fixture.getId(), edge.getId());
        assertEquals(fixture.getLabel(), edge.getLabel());
        assertEquals(fixture.getProperties(), edge.getProperties());
        assertEquals(fixture.getSourceId(), edge.getSourceId());
        assertEquals(fixture.getTargetId(), edge.getTargetId());
        assertEquals(fixture.getGraphIds(), edge.getGraphIds());
        assertEquals(fixture.hashCode(), edge.hashCode());
        assertTrue(!edge.equals(null));
    }

}