package au.com.d2dcrc.ia.search.ingestor.extractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import au.com.d2dcrc.ia.search.graph.EpgEdge;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.ingestor.error.EpgMissingFieldException;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import java.util.Collections;
import org.junit.Test;

/**
 * Tests the implementation of {@link EpgEdgeExtractor}.
 */
public class EpgEdgeExtractorTest {

    /**
     * Tests against valid edge data.
     */
    @Test
    public void testEdge() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"source\":\"y\","
                + "\"target\":\"z\",\"meta\":{\"label\":\"w\",\"graphs\":[\"g\"]}}";

        EpgEdge elem = new EpgEdgeExtractor().extract(json);

        assertNotNull(elem);
        assertEquals("x", elem.getId());
        assertEquals("y", elem.getSourceId());
        assertEquals("z", elem.getTargetId());
        assertEquals("w", elem.getLabel());
        assertNotNull(elem.getProperties());
        assertEquals(new EpgProperties("d", 3), elem.getProperties());
        assertNotNull(elem.getGraphIds());
        assertEquals(1, elem.getGraphIds().size());
        assertEquals(Collections.singleton("g"), elem.getGraphIds());
    }

    /**
     * Tests against invalid (truncated) edge data.
     */
    @Test(expected = EpgSyntaxException.class)
    public void testEdgeBroken() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"meta\":{\"label\":\"w\"";

        @SuppressWarnings("unused")
        EpgEdge elem = new EpgEdgeExtractor().extract(json);
    }

    /**
     * Tests against invalid edge data with missing identifier.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testEdgeWithMissingId() {
        final String json = "{\"data\":{\"d\":3},\"source\":\"y\","
                + "\"target\":\"z\",\"meta\":{\"label\":\"w\",\"graphs\":[\"g\"]}}";

        @SuppressWarnings("unused")
        EpgEdge elem = new EpgEdgeExtractor().extract(json);
    }

    /**
     * Tests against invalid edge data with missing source.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testEdgeWithMissingSource() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},"
                + "\"target\":\"z\",\"meta\":{\"label\":\"w\",\"graphs\":[\"g\"]}}";

        @SuppressWarnings("unused")
        EpgEdge elem = new EpgEdgeExtractor().extract(json);
    }

    /**
     * Tests against invalid edge data with missing target.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testEdgeWithMissingTarget() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"source\":\"y\","
                + "\"meta\":{\"label\":\"w\",\"graphs\":[\"g\"]}}";

        @SuppressWarnings("unused")
        EpgEdge elem = new EpgEdgeExtractor().extract(json);
    }

    /**
     * Tests against invalid edge data with missing data.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testEdgeWithMissingData() {
        final String json = "{\"id\":\"x\",\"source\":\"y\","
                + "\"target\":\"z\",\"meta\":{\"label\":\"w\",\"graphs\":[\"g\"]}}";

        @SuppressWarnings("unused")
        EpgEdge elem = new EpgEdgeExtractor().extract(json);
    }

    /**
     * Tests against invalid edge data with missing meta-data.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testEdgeWithMissingMetaData() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"source\":\"y\","
                + "\"target\":\"z\"}";

        @SuppressWarnings("unused")
        EpgEdge elem = new EpgEdgeExtractor().extract(json);
    }

    /**
     * Tests against invalid edge data with missing meta-data label.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testEdgeWithMissingMetaDataLabel() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"source\":\"y\","
                + "\"target\":\"z\",\"meta\":{\"graphs\":[\"g\"]}}";

        @SuppressWarnings("unused")
        EpgEdge elem = new EpgEdgeExtractor().extract(json);
    }

    /**
     * Tests against invalid edge data with missing meta data graphs.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testEdgeWithMissingMetaDataGraphs() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"source\":\"y\","
                + "\"target\":\"z\",\"meta\":{\"label\":\"w\"}}";

        @SuppressWarnings("unused")
        EpgEdge elem = new EpgEdgeExtractor().extract(json);
    }

}
