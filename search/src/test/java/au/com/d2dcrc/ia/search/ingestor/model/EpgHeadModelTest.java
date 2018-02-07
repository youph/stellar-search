package au.com.d2dcrc.ia.search.ingestor.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import org.junit.Test;

/**
 * Tests the JSON construction of {@link EpgHeadModel}.
 */
public class EpgHeadModelTest {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Tests against the IMBD head data.
     * 
     * @throws IOException if something bad happens.
     */
    @Test
    public void testIMDBEdge() throws IOException {
        final URL jsonResource = getClass().getResource("imdb-head.json");

        EpgHeadModel model = mapper.readValue(jsonResource, EpgHeadModel.class);

        assertNotNull(model);
        model.validate();
        assertEquals("342D5869102440778B69FDF03756C858", model.getId());
        assertTrue(model.getData().isEmpty());
        assertEquals("imdb", model.getMetaData().getLabel());
    }

    /**
     * Tests against the Yelp head data.
     * 
     * @throws IOException if something bad happens.
     */
    @Test
    public void testYelpEdge() throws IOException {
        final URL jsonResource = getClass().getResource("yelp-head.json");

        EpgHeadModel model = mapper.readValue(jsonResource, EpgHeadModel.class);

        assertNotNull(model);
        model.validate();
        assertEquals("59ace22b1f9e746b6e5fe2bd", model.getId());
        assertTrue(model.getData().isEmpty());
        assertEquals("small_yelp_example", model.getMetaData().getLabel());
    }

}
