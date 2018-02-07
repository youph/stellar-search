package au.com.d2dcrc.ia.search.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.Test;

/**
 * Tests that the {@link EpgVertex} object is correctly serialised into JSON and
 * deserialised from JSON.
 */
public class EpgVertexTest {

    private final EpgVertex fixture = new EpgVertex(
        "342D5869102440778B69FDF03756C853",
        "iamanode",
        new EpgProperties("name", "Slow Fred", "IQ", 42),
        Collections.singleton("342D5869102440778B69FDF03756C858")
    );

    @Test
    public void testSerializeDeserialize() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fixture);
        EpgVertex vertex = mapper.readValue(json, EpgVertex.class);
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