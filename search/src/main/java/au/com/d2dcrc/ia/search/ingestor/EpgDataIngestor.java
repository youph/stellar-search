package au.com.d2dcrc.ia.search.ingestor;

import au.com.d2dcrc.ia.search.graph.EpgEdge;
import au.com.d2dcrc.ia.search.graph.EpgHead;
import au.com.d2dcrc.ia.search.graph.EpgVertex;
import au.com.d2dcrc.ia.search.ingestor.error.EpgDataException;
import au.com.d2dcrc.ia.search.ingestor.error.EpgElementMissingException;
import au.com.d2dcrc.ia.search.ingestor.error.EpgIngestionException;
import au.com.d2dcrc.ia.search.ingestor.extractor.EpgEdgeExtractor;
import au.com.d2dcrc.ia.search.ingestor.extractor.EpgHeadExtractor;
import au.com.d2dcrc.ia.search.ingestor.extractor.EpgVertexExtractor;
import au.com.d2dcrc.ia.search.management.EpgReferenceModel;
import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Ingests EPG graphs from raw data.
 */
public class EpgDataIngestor {

    private final EpgElementContainer<EpgHead> graphSet;
    private final EpgElementContainer<EpgVertex> vertexSet;
    private final EpgElementContainer<EpgEdge> edgeSet;

    /**
     * Constructs an EPG graph ingestor linked to the specified EPG element storage sets.
     * 
     * @param graphSet - The holder for a set of graph heads.
     * @param vertexSet - The holder for a set of graph vertices.
     * @param edgeSet - The holder for a set of graph edges.
     */
    public EpgDataIngestor(
            final EpgElementContainer<EpgHead> graphSet,
            final EpgElementContainer<EpgVertex> vertexSet,
            final EpgElementContainer<EpgEdge> edgeSet
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
        if (graphData != null && "file".equals(graphData.getScheme())) {
            new EpgElementFileIngestor<EpgHead>(graphSet, new EpgHeadExtractor()).ingest(getFilePath(graphData));
        } else {
            throw new EpgDataException("Unhandled scheme for URI: " + graphData);
        }
    }

    private Path getFilePath(URI uriPath) {
        try {
            return Paths.get(uriPath);
        } catch (IllegalArgumentException | FileSystemNotFoundException e) {
            throw new EpgDataException("Invalid file path URI: " + uriPath);
        }
    }

    private void ingestVertices(URI vertexData) {
        if (vertexData != null && "file".equals(vertexData.getScheme())) {
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
        if (edgeData != null && "file".equals(edgeData.getScheme())) {
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
