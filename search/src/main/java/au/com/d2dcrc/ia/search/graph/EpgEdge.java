package au.com.d2dcrc.ia.search.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;

/**
 * Specifies a mutable EPG edge.
 */
public class EpgEdge extends EpgElement {

    @JsonProperty("source")
    private String sourceId;

    @JsonProperty("target")
    private String targetId;

    @JsonProperty("graphs")
    private Set<String> graphIds = new HashSet<>();

    /**
     * Constructs an empty edge.
     */
    public EpgEdge() {
        super();
    }

    /**
     * Constructs an EPG edge.
     * 
     * @param id - The edge identifier.
     * @param label - The edge label.
     * @param properties - The edge properties.
     * @param sourceId - The source vertex identifier.
     * @param targetId - The target vertex identifier.
     * @param graphs - The identifiers of the graphs to which the vertex belongs.
     */
    @JsonCreator
    public EpgEdge(
        @JsonProperty("id") String id, 
        @JsonProperty("label") String label, 
        @JsonProperty("properties") EpgProperties properties,
        @JsonProperty("source")
        String sourceId,
        @JsonProperty("target")
        String targetId,
        @JsonProperty("graphs") Set<String> graphs
    ) {
        super(id, label, properties);
        this.sourceId = sourceId;
        this.targetId = targetId;
        setGraphIds(graphs);
    }

    /**
     * Obtains the identifier of the vertex from which the edge originates.
     * 
     * @return The source vertex identifier.
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Specifies the identifier of the vertex from which the edge originates.
     * 
     * @param sourceId - The source vertex identifier.
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * Obtains the identifier of the vertex to which the edge is directed.
     * 
     * @return The target vertex identifier.
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * Specifies the identifier of the vertex to which the edge is directed.
     * 
     * @param targetId - The target vertex identifier.
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * Obtains the identifiers of the graphs of which the edge is a member.
     * 
     * @return A (possibly empty) set of graph identifiers.
     */
    public Set<String> getGraphIds() {
        return graphIds;
    }

    /**
     * Specifies the identifiers of the graphs of which the edge is a member.
     * 
     * @param graphIds - A (possibly empty) set of graph identifiers.
     */
    public void setGraphIds(Set<String> graphIds) {
        this.graphIds = (graphIds == null) ? new HashSet<>() : graphIds;
    }

    @Override
    public int hashCode() {
        int h1 = (sourceId == null) ? 0 : sourceId.hashCode();
        int h2 = (targetId == null) ? 0 : targetId.hashCode();
        return 31 * (31 * (31 * super.hashCode() + graphIds.hashCode()) + h1) + h2;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof EpgEdge)) {
            return false;
        }
        EpgEdge otherEdge = (EpgEdge) other;
        return match(sourceId, otherEdge.getSourceId())
            && match(targetId, otherEdge.getTargetId())
            && super.equals(otherEdge)
            && graphIds.equals(otherEdge.getGraphIds());
    }

}
