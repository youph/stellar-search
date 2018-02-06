package au.com.d2dcrc.ia.search.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;

/**
 * Specifies a mutable EPG vertex element.
 */
public class EpgVertex extends EpgElement {

    @JsonProperty("graphs")
    private Set<String> graphIds = new HashSet<>();

    /**
     * Constructs an empty EPG vertex.
     */
    public EpgVertex() {
        super();
    }

    /**
     * Constructs an EPG vertex.
     * 
     * @param id - The vertex identifier.
     * @param label - The vertex label.
     * @param properties - The vertex properties.
     * @param graphs - The identifiers of the graphs to which the vertex belongs.
     */
    @JsonCreator
    public EpgVertex(
        @JsonProperty("id") String id, 
        @JsonProperty("label") String label, 
        @JsonProperty("properties") EpgProperties properties,
        @JsonProperty("graphs") Set<String> graphs
    ) {
        super(id, label, properties);
        setGraphIds(graphs);
    }

    /**
     * Obtains the identifiers of the graphs of which the vertex is a member.
     * 
     * @return A (possibly empty) collection of graph identifiers.
     */
    @JsonProperty("graphs")
    public Set<String> getGraphIds() {
        return graphIds;
    }

    /**
     * Specifies the identifiers of the graphs of which the vertex is a member.
     * 
     * @param graphIds - A (possibly empty) set of graph identifiers.
     */
    public void setGraphIds(Set<String> graphIds) {
        this.graphIds = (graphIds == null) ? new HashSet<>() : graphIds;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + graphIds.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) 
            && (other instanceof EpgVertex)
            && graphIds.equals(((EpgVertex) other).getGraphIds());
    }

}
