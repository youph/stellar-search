package au.com.d2dcrc.ia.search.graph;

import java.util.Collection;

/**
 * Defines an EPG edge that is modifiable.
 */
public interface MutableEpgEdge extends EpgEdge, MutableEpgElement {

    /**
     * Specifies the unique identifier of the edge.
     * 
     * @param id - The edge identifier.
     */
    @Override
    public void setId(String id);

    /**
     * Specifies the label of the edge.
     * 
     * @param label - The edge label.
     */
    @Override
    public void setLabel(String label);

    /**
     * Obtains the mutable properties of the edge.
     * 
     * @return The edge properties.
     */
    @Override
    MutableEpgProperties getProperties();

    /**
     * Specifies the properties of the edge. A copy might be made if the given
     * properties are immutable.
     * 
     * @param properties - The edge properties.
     */
    @Override
    public void setProperties(EpgProperties properties);

    /**
     * Obtains the identifiers of the graphs of which the edge is a member.
     * These are mutable.
     * 
     * @return A (possibly empty) collection of graph identifiers.
     */
    @Override
    Collection<String> getGraphIds();

    /**
     * Specifies the identifiers of the graphs of which the edge is a member. A
     * copy might be made if the given collection is immutable.
     * 
     * @param graphIds - A (possibly empty) collection of graph identifiers.
     */
    void setGraphIds(Collection<String> graphIds);

    /**
     * Specifies the identifier of the vertex from which the edge originates.
     * 
     * @param sourceId - The source vertex identifier.
     */
    void setSourceId(String sourceId);

    /**
     * Specifies the identifier of the vertex to which the edge is directed.
     * 
     * @param targetId - The target vertex identifier.
     */
    void setTargetId(String targetId);

}
