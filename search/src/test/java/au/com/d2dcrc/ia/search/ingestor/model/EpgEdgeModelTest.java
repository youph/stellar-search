package au.com.d2dcrc.ia.search.ingestor.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import org.junit.Test;

/**
 * Tests the JSON construction of {@link EpgEdgeModel}.
 */
public class EpgEdgeModelTest {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Tests against the IMBD edge data.
     * 
     * @throws IOException if something bad happens.
     */
    @Test
    public void testIMDBEdge() throws IOException {
        final URL jsonResource = getClass().getResource("imdb-edge.json");

        EpgEdgeModel model = mapper.readValue(jsonResource, EpgEdgeModel.class);

        assertNotNull(model);
        model.validate();
        assertEquals("4DAC30B7DC91434C8FFC42956DA890CF", model.getId());
        assertEquals("A857A9E226F647AC9DA2ACF140401855", model.getSourceId());
        assertEquals("5BE915F9F39F4005B7925EDE98D53601", model.getTargetId());
        assertTrue(model.getData().isEmpty());
        assertEquals("produced", model.getMetaData().getLabel());
        assertThat(model.getMetaData().getGraphIds(), arrayWithSize(1));
        assertArrayEquals(new String[] { "342D5869102440778B69FDF03756C858" }, model.getMetaData().getGraphIds());
    }

    /**
     * Tests against the Yelp edge data.
     * 
     * @throws IOException if something bad happens.
     */
    @Test
    public void testYelpEdge() throws IOException {
        final URL jsonResource = getClass().getResource("yelp-edge.json");

        EpgEdgeModel model = mapper.readValue(jsonResource, EpgEdgeModel.class);

        assertNotNull(model);
        model.validate();
        assertEquals("59ace2fa1f9e746b6e5ff44f", model.getId());
        assertEquals("59ace2de1f9e746b6e5fef7d", model.getSourceId());
        assertEquals("59ace2fa1f9e746b6e5ff44e", model.getTargetId());
        EpgProperties data = new EpgProperties("fromYelpId", "PwSHyuhGQu_YzeoB2dlu3Q", "toYelpId", "Sainte-Adele");
        assertEquals(model.getData(), data); // XXX Deliberately swapped expected with actual!
        assertEquals("locatedIn", model.getMetaData().getLabel());
        assertThat(model.getMetaData().getGraphIds(), arrayWithSize(1));
        assertArrayEquals(new String[] { "59ace22b1f9e746b6e5fe2bd" }, model.getMetaData().getGraphIds());
    }

}
