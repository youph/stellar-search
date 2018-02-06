package au.com.d2dcrc.ia.search.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies a mutable EPG graph head element.
 */
public class EpgGraph extends EpgElement {

    /**
     * Constructs an empty EPG graph head.
     */
    public EpgGraph() {
        super();
    }

    /**
     * Constructs an EPG graph head.
     * 
     * @param id - The graph identifier.
     * @param label - The graph label.
     * @param properties - The graph properties.
     */
    @JsonCreator
    public EpgGraph(
        @JsonProperty("id") String id, 
        @JsonProperty("label") String label, 
        @JsonProperty("properties") EpgProperties properties
    ) {
        super(id, label, properties);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof EpgGraph) && super.equals(other);
    }

}
