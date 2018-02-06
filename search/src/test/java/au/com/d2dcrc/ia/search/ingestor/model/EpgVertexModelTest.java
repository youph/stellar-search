package au.com.d2dcrc.ia.search.ingestor.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests the JSON construction of {@link EpgVertexModel}.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class EpgVertexModelTest {

    @Inject
    private ObjectMapper mapper;

    /**
     * Tests against the IMBD vertex data.
     * 
     * @throws IOException if something bad happens.
     */
    @Test
    public void testIMDBEdge() throws IOException {
        final URL jsonResource = getClass().getResource("imdb-vertex.json");

        EpgVertexModel model = mapper.readValue(jsonResource, EpgVertexModel.class);

        assertNotNull(model);
        assertEquals("6D6B58B73E834967B4BC0F3B54A790EF", model.getId());
        assertNotNull(model.getData());
        EpgProperties data = new EpgProperties("__id", " Amanda Phillips", "name", " Amanda Phillips");
        assertEquals(model.getData(), data); // XXX Deliberately swapped expected with actual!
        assertNotNull(model.getMetaData());
        assertEquals("Person", model.getMetaData().getLabel());
        assertThat(model.getMetaData().getGraphIds(), arrayWithSize(1));
        assertArrayEquals(new String[] { "342D5869102440778B69FDF03756C858" }, model.getMetaData().getGraphIds());
    }

    /**
     * Tests against the Yelp vertex data.
     * 
     * @throws IOException if something bad happens.
     */
    @Test
    public void testYelpEdge() throws IOException {
        final URL jsonResource = getClass().getResource("yelp-vertex.json");

        EpgVertexModel model = mapper.readValue(jsonResource, EpgVertexModel.class);

        assertNotNull(model);
        assertEquals("59ace2bc1f9e746b6e5fed0e", model.getId());
        assertNotNull(model.getData());
        EpgProperties data = new EpgProperties(
            "compliment_cool", "150", "cool", "127", 
            "yelpId", "d0D7L-vfQDIADolnPAcb9A", "elite", "true"
        );
        assertEquals(model.getData(), data); // XXX Deliberately swapped expected with actual!
        assertNotNull(model.getMetaData());
        assertEquals("user", model.getMetaData().getLabel());
        assertThat(model.getMetaData().getGraphIds(), arrayWithSize(1));
        assertArrayEquals(new String[] { "59ace22b1f9e746b6e5fe2bd" }, model.getMetaData().getGraphIds());
    }

}
