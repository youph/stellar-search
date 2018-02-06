package au.com.d2dcrc.ia.search.ingestor;

import au.com.d2dcrc.ia.search.graph.EpgEdge;
import au.com.d2dcrc.ia.search.graph.EpgHead;
import au.com.d2dcrc.ia.search.graph.EpgVertex;
import au.com.d2dcrc.ia.search.management.EpgReferenceModel;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;

/**
 * Ingests EPG graphs from raw data.
 */
public class EpgDataIngestor {

    private final QueryableEpgElementStore<EpgHead> graphSet;
    private final QueryableEpgElementStore<EpgVertex> vertexSet;
    private final QueryableEpgElementStore<EpgEdge> edgeSet;

    /**
     * Constructs an EPG graph ingestor linked to the specified EPG element storage sets.
     * 
     * @param graphSet - The holder for a set of graph heads.
     * @param vertexSet - The holder for a set of graph vertices.
     * @param edgeSet - The holder for a set of graph edges.
     */
    public EpgDataIngestor(
            final QueryableEpgElementStore<EpgHead> graphSet,
            final QueryableEpgElementStore<EpgVertex> vertexSet,
            final QueryableEpgElementStore<EpgEdge> edgeSet
    ) {
        this.graphSet = graphSet;
        this.vertexSet = vertexSet;
        this.edgeSet = edgeSet;
    }

    /**
     * Ingests the EPG graph data from the specified model into the pre-defined
     * storage sets.
     * 
     * @param epgData - The source(s) of the EPG graph data.
     * @throws EpgIngestionException if the graph data are not ingestible from any of their respective sources.
     */
    public void ingest(final EpgReferenceModel epgData) throws EpgIngestionException {
        // XXX This order of extraction is required for verification of identifiers.
        ingestGraphHeads(epgData.getGraphs());
        ingestVertices(epgData.getVertices());
        ingestEdges(epgData.getEdges());
    }

    private void ingestGraphHeads(URI graphData) {
        String scheme = graphData.getScheme();
        if ("file".equals(scheme)) {
            new EpgElementFileIngestor<EpgHead>(graphSet, new EpgHeadExtractor()).ingest(getFilePath(graphData));
        } else {
            throw new EpgDataException("Unhandled scheme for URI: " + graphData);
        }
    }

    private Path getFilePath(URI uriPath) {
        String path = uriPath.getPath();
        if (path == null || path.trim().isEmpty()) {
            throw new EpgDataException("Invalid file path URI: " + uriPath);
        }
        return new File(path).toPath();
    }

    private void ingestVertices(URI vertexData) {
        String scheme = vertexData.getScheme();
        if ("file".equals(scheme)) {
            new EpgElementFileIngestor<EpgVertex>(this::validateVertex, new EpgVertexExtractor())
                .ingest(getFilePath(vertexData));
        } else {
            throw new EpgDataException("Unhandled scheme for URI: " + vertexData);
        }
    }

    private void validateVertex(EpgVertex vertex) {
        for (String graphId : vertex.getGraphIds()) {
            if (!graphSet.hasElement(graphId)) {
                throw new EpgElementMissingException(
                    "Vertex "  + vertex.getId() + " references non-existent graph head " + graphId
                );
            }
        }
        vertexSet.addElement(vertex);
    }

    private void ingestEdges(URI edgeData) {
        String scheme = edgeData.getScheme();
        if ("file".equals(scheme)) {
            new EpgElementFileIngestor<EpgEdge>(this::validateEdge, new EpgEdgeExtractor())
                .ingest(getFilePath(edgeData));
        } else {
            throw new EpgDataException("Unhandled scheme for URI: " + edgeData);
        }
    }

    private void validateEdge(EpgEdge edge) {
        if (!vertexSet.hasElement(edge.getSourceId())) {
            throw new EpgElementMissingException(
                    "Edge "  + edge.getId() + " references non-existent source vertex " + edge.getSourceId()
                );
        }
        if (!vertexSet.hasElement(edge.getTargetId())) {
            throw new EpgElementMissingException(
                    "Edge "  + edge.getId() + " references non-existent target vertex " + edge.getTargetId()
                );
        }
        // TODO Validate that the graph IDs of the edge are the union of the
        // source and target vertex graph IDs.
        for (String graphId : edge.getGraphIds()) {
            if (!graphSet.hasElement(graphId)) {
                throw new EpgElementMissingException(
                    "Edge "  + edge.getId() + " references non-existent graph head " + graphId
                );
            }
        }
        edgeSet.addElement(edge);
    }

}
