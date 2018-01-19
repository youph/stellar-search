package au.com.d2dcrc.ia.search.graph.impl;

import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.graph.EpgVertex;
import au.com.d2dcrc.ia.search.graph.MutableEpgVertex;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@JsonDeserialize(using = EpgVertexImplDeserializer.class)
public class EpgVertexImpl extends EpgElementImpl implements MutableEpgVertex {

    @JsonProperty("graphs")
    private Set<String> graphIds = new HashSet<>();

    /**
     * Default constructor.
     */
    public EpgVertexImpl() {
        super();
    }

    /**
     * Simple constructor.
     * 
     * @param id
     *            - The graph identifier.
     * @param label
     *            - The graph label.
     * @param properties
     *            - The graph properties.
     * @param graphs
     *            - The graph identifiers.
     */
    @JsonCreator
    public EpgVertexImpl(String id, String label, EpgProperties properties, Collection<String> graphs) {
        super(id, label, properties);
        setGraphIds(graphs);
    }

    @Override
    public Collection<String> getGraphIds() {
        return graphIds;
    }

    @Override
    public void setGraphIds(Collection<String> graphIds) {
        this.graphIds = (graphIds == null) ? new HashSet<>() : new HashSet<>(graphIds);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + graphIds.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof EpgVertex) 
            && super.equals(other) 
            && graphIds.equals(((EpgVertex) other).getGraphIds());
    }

}
