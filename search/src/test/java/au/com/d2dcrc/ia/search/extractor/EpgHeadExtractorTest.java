package au.com.d2dcrc.ia.search.extractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import au.com.d2dcrc.ia.search.graph.EpgHead;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.ingestor.error.EpgMissingFieldException;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import au.com.d2dcrc.ia.search.ingestor.extractor.EpgHeadExtractor;
import org.junit.Test;

/**
 * Tests the implementation of {@link EpgHeadExtractor}.
 */
public class EpgHeadExtractorTest {

    /**
     * Tests against valid head data.
     */
    @Test
    public void testHead() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"meta\":{\"label\":\"w\"}}";

        EpgHead elem = new EpgHeadExtractor().extract(json);

        assertNotNull(elem);
        assertEquals("x", elem.getId());
        assertEquals("w", elem.getLabel());
        assertNotNull(elem.getProperties());
        assertEquals(new EpgProperties("d", 3), elem.getProperties());
    }

    /**
     * Tests against invalid (truncated) head data.
     */
    @Test(expected = EpgSyntaxException.class)
    public void testHeadBroken() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"meta\":{\"label\":\"w\"";

        @SuppressWarnings("unused")
        EpgHead elem = new EpgHeadExtractor().extract(json);
    }

    /**
     * Tests against invalid head data with missing identifier.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testHeadWithMissingId() {
        final String json = "{\"data\":{\"d\":3},\"meta\":{\"label\":\"w\"}}";

        @SuppressWarnings("unused")
        EpgHead elem = new EpgHeadExtractor().extract(json);
    }

    /**
     * Tests against invalid head data with missing data.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testHeadWithMissingData() {
        final String json = "{\"id\":\"x\",\"meta\":{\"label\":\"w\"}}";

        @SuppressWarnings("unused")
        EpgHead elem = new EpgHeadExtractor().extract(json);
    }

    /**
     * Tests against invalid head data with missing meta-data.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testHeadWithMissingMetaData() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3}}";

        @SuppressWarnings("unused")
        EpgHead elem = new EpgHeadExtractor().extract(json);
    }

    /**
     * Tests against invalid head data with missing meta-data label.
     */
    @Test(expected = EpgMissingFieldException.class)
    public void testHeadWithMissingMetaDataLabel() {
        final String json = "{\"id\":\"x\",\"data\":{\"d\":3},\"meta\":{}}";

        @SuppressWarnings("unused")
        EpgHead elem = new EpgHeadExtractor().extract(json);
    }

}
