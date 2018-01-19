package au.com.d2dcrc.ia.search.graph.impl;

import au.com.d2dcrc.ia.search.graph.EpgGraph;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.graph.MutableEpgGraph;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = EpgGraphImplDeserializer.class)
public class EpgGraphImpl extends EpgElementImpl implements MutableEpgGraph {

    /**
     * Default constructor.
     */
    public EpgGraphImpl() {
        super();
    }

    /**
     * Simple constructor.
     * 
     * @param id - The graph identifier.
     * @param label - The graph label.
     * @param properties - The graph properties.
     */
    @JsonCreator
    public EpgGraphImpl(String id, String label, EpgProperties properties) {
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
