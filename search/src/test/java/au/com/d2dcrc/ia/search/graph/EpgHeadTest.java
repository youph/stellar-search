package au.com.d2dcrc.ia.search.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * Tests that the {@link EpgHead} object is correctly serialised into JSON and
 * deserialised from JSON.
 */
public class EpgHeadTest {

    private final EpgHead fixture = new EpgHead(
        "342D5869102440778B69FDF03756C858",
        "iamagraph",
        new EpgProperties("key1", "value1", "key2", 42, "key3", true)
    );

    @Test
    public void testSerializeDeserialize() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fixture);
        EpgHead graph = mapper.readValue(json, EpgHead.class);
        assertEquals(fixture, graph);
    }

    @Test
    public void testMethods() throws Exception {
        EpgHead graph = new EpgHead();
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