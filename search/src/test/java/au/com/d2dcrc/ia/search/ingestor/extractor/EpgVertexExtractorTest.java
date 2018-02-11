package au.com.d2dcrc.ia.search.ingestor.extractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.graph.EpgVertex;
import au.com.d2dcrc.ia.search.ingestor.error.EpgMissingFieldException;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import au.com.d2dcrc.ia.search.ingestor.extractor.EpgVertexExtractor;
import java.util.Collections;
import org.junit.Test;

/**
 * Tests the implementation of {@link EpgVertexExtractor}.
 */
public class EpgVertexExtractorTest {

    /**
     * Tests against valid vertex data.
     */
    @Test
    public void testVertex() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"meta\":{\"label\":\"w\",\"graphs\":[\"g\"]}}";

        EpgVertex elem = new EpgVertexExtractor().extract(json);

        assertNotNull(elem);
        assertEquals("x", elem.getId());
        assertEquals("w", elem.getLabel());
        assertNotNull(elem.getProperties());
        assertEquals(new EpgProperties("d", 3), elem.getProperties());
        assertNotNull(elem.getGraphIds());
        assertEquals(1, elem.getGraphIds().size());
        assertEquals(Collections.singleton("g"), elem.getGraphIds());
    }

    /**
     * Tests against invalid (truncated) vertex data.
     */
    @Test(expected = EpgSyntaxException.class)
    public void testVertexBroken() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"meta\":{\"label\":\"w\"";

        @SuppressWarnings("unused")
        EpgVertex elem = new EpgVertexExtractor().extract(json);
    }

    /**
     * Tests against invalid vertex data with missing identifier.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testVertexWithMissingId() {
        final String json = "{\"data\":{\"d\":3},\"meta\":{\"label\":\"w\",\"graphs\":[\"g\"]}}";

        @SuppressWarnings("unused")
        EpgVertex elem = new EpgVertexExtractor().extract(json);
    }

    /**
     * Tests against invalid vertex data with missing data.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testVertexWithMissingData() {
        final String json = "{\"id\":\"x\",\"meta\":{\"label\":\"w\",\"graphs\":[\"g\"]}}";

        @SuppressWarnings("unused")
        EpgVertex elem = new EpgVertexExtractor().extract(json);
    }

    /**
     * Tests against invalid vertex data with missing meta-data.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testVertexWithMissingMetaData() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3}}";

        @SuppressWarnings("unused")
        EpgVertex elem = new EpgVertexExtractor().extract(json);
    }

    /**
     * Tests against invalid vertex data with missing meta-data label.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testVertexWithMissingMetaDataLabel() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"meta\":{\"graphs\":[\"g\"]}}";

        @SuppressWarnings("unused")
        EpgVertex elem = new EpgVertexExtractor().extract(json);
    }

    /**
     * Tests against invalid vertex data with missing meta data graphs.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testVertexWithMissingMetaDataGraphs() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"meta\":{\"label\":\"w\"}}";

        @SuppressWarnings("unused")
        EpgVertex elem = new EpgVertexExtractor().extract(json);
    }

}
