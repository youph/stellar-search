package au.com.d2dcrc.ia.search.ingestor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import au.com.d2dcrc.ia.search.graph.EpgEdge;
import au.com.d2dcrc.ia.search.graph.EpgElement;
import au.com.d2dcrc.ia.search.graph.EpgHead;
import au.com.d2dcrc.ia.search.graph.EpgVertex;
import au.com.d2dcrc.ia.search.ingestor.error.EpgDataException;
import au.com.d2dcrc.ia.search.ingestor.error.EpgElementExistsException;
import au.com.d2dcrc.ia.search.ingestor.error.EpgElementMissingException;
import au.com.d2dcrc.ia.search.management.EpgReferenceModel;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Tests the implementation of {@link EpgDataIngestor}.
 */
public class EpgDataIngestorTest {

    /**
     * Tests against the IMBD flat data set.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test
    public void testIMDBIngestion() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = getIMDBFlat("", "", "");
        EpgDataIngestor ingestor = getDataIngestor();
        ingestor.ingest(dataSource);
    }

    private EpgReferenceModel getIMDBFlat(String head, String vertex, String edge) throws URISyntaxException {
        URI headResource = getResourceURI("./imdb-flat/imdb-heads" + head + ".json");
        URI vertexResource = getResourceURI("./imdb-flat/imdb-vertices" + vertex + ".json");
        URI edgeResource = getResourceURI("./imdb-flat/imdb-edges" + edge + ".json");
        return new EpgReferenceModel(headResource, vertexResource, edgeResource);
    }

    private URI getResourceURI(String path) throws URISyntaxException {
        return getClass().getResource(path).toURI();
    }

    private EpgDataIngestor getDataIngestor() {
        EpgElementContainer<EpgHead> epgHeads = new EpgElementsMap<>();
        EpgElementContainer<EpgVertex> epgVertices = new EpgElementsMap<>();
        EpgElementContainer<EpgEdge> epgEdges = new EpgElementsMap<>();
        EpgDataIngestor ingestor = new EpgDataIngestor(epgHeads, epgVertices, epgEdges);
        return ingestor;
    }

    /**
     * Tests against the IMBD flat data set but with a head having a duplicate identifier.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test(expected = EpgElementExistsException.class)
    public void testIMDBMIngestionWithDuplicateHead() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = getIMDBFlat("-duplicate", "", "");
        EpgDataIngestor ingestor = getDataIngestor();
        ingestor.ingest(dataSource);
    }

    /**
     * Tests against the IMBD flat data set but with a vertex having a missing head reference.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test(expected = EpgElementMissingException.class)
    public void testIMDBMIngestionWithissingHead() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = getIMDBFlat("", "-missing-head", "");
        EpgDataIngestor ingestor = getDataIngestor();
        ingestor.ingest(dataSource);
    }

    /**
     * Tests against the IMBD flat data set but with an edge having a missing vertex source reference.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test(expected = EpgElementMissingException.class)
    public void testIMDBIngestionWithMissingSourceVertex() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = getIMDBFlat("", "", "-missing-source");
        EpgDataIngestor ingestor = getDataIngestor();
        ingestor.ingest(dataSource);
    }

    /**
     * Tests against the IMBD flat data set but with an edge having a missing vertex target reference.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test(expected = EpgElementMissingException.class)
    public void testIMDBIngestionWithMissingTargetVertex() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = getIMDBFlat("", "", "-missing-target");
        EpgDataIngestor ingestor = getDataIngestor();
        ingestor.ingest(dataSource);
    }

    /**
     * Tests against the IMBD flat data set but with an edge having a missing head reference.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test(expected = EpgElementMissingException.class)
    public void testIMDBIngestionWithEdgeMissingHead() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = getIMDBFlat("", "", "-missing-head");
        EpgDataIngestor ingestor = getDataIngestor();
        ingestor.ingest(dataSource);
    }

    /**
     * Tests against the IMBD nested data set.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test
    public void testIMDBNestedIngestion() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = getIMDBNested();
        EpgDataIngestor ingestor = getDataIngestor();
        ingestor.ingest(dataSource);
    }

    private EpgReferenceModel getIMDBNested() throws URISyntaxException {
        URI headResource = getResourceURI("./imdb-nested/heads");
        URI vertexResource = getResourceURI("./imdb-nested/vertices");
        URI edgeResource = getResourceURI("./imdb-nested/edges");
        return new EpgReferenceModel(headResource, vertexResource, edgeResource);
    }

    /**
     * Tests more fully against the IMBD nested data set.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test
    public void testIMDBNestedIngestionAgain() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = getIMDBNested();
        EpgElementsMap<EpgHead> epgHeads = new EpgElementsMap<>();
        EpgElementsMap<EpgVertex> epgVertices = new EpgElementsMap<>();
        EpgElementsMap<EpgEdge> epgEdges = new EpgElementsMap<>();
        EpgDataIngestor ingestor = new EpgDataIngestor(epgHeads, epgVertices, epgEdges);
        ingestor.ingest(dataSource);
        assertEquals(1, toList(epgHeads).size());
        assertTrue(epgHeads.hasElement("342D5869102440778B69FDF03756C858"));
        assertNotNull(epgHeads.getElement("342D5869102440778B69FDF03756C858"));
        assertEquals(3, toList(epgEdges).size());
        assertTrue(epgEdges.hasElement("4DAC30B7DC91434C8FFC42956DA890CF"));
        assertTrue(epgEdges.hasElement("E4278A8645784AC09A9A4505E7AF5CF5"));
        assertTrue(epgEdges.hasElement("8C56F392682F460A96A4309340B5E6D7"));
        assertEquals(5, toList(epgVertices).size());
        assertTrue(epgVertices.hasElement("A857A9E226F647AC9DA2ACF140401855"));
        assertTrue(epgVertices.hasElement("5BE915F9F39F4005B7925EDE98D53601"));
        assertTrue(epgVertices.hasElement("C7985AF3400D4F4B9DB20199162CB59B"));
        assertTrue(epgVertices.hasElement("D749A2B1061840ECA41B886D3EB2A0C5"));
        assertTrue(epgVertices.hasElement("F566BBC0DC64412BA613C10D807ECC79"));
    }

    private <T extends EpgElement> List<T> toList(EpgElementsMap<T> map) {
        List<T> list = new ArrayList<>();
        for (T elem : map) {
            list.add(elem);
        }
        return list;
    }

    /**
     * Tests against a non-file-schema path.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test(expected = EpgDataException.class)
    public void testNonFilePath() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = new EpgReferenceModel(null, null, null);
        EpgDataIngestor ingestor = getDataIngestor();
        ingestor.ingest(dataSource);
    }

    /**
     * Tests against a bad path.
     * 
     * @throws IOException if something bad happens.
     * @throws URISyntaxException if the resource path fails.
     */
    @Test(expected = EpgDataException.class)
    public void testBadPath() throws IOException, URISyntaxException {
        EpgReferenceModel dataSource = new EpgReferenceModel(getBadURI(), getBadURI(), getBadURI());
        EpgDataIngestor ingestor = getDataIngestor();
        ingestor.ingest(dataSource);
    }

    private URI getBadURI() throws URISyntaxException {
        String path = getClass().getResource("").getPath();
        return new URI("file://" + path + "/XXX");
    }

}
