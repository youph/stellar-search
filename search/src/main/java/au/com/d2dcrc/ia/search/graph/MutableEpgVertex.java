package au.com.d2dcrc.ia.search.graph;

import java.util.Collection;

/**
 * Defines an EPG vertex that is modifiable.
 */
public interface MutableEpgVertex extends EpgVertex, MutableEpgElement {

    /**
     * Specifies the unique identifier of the vertex.
     * 
     * @param id - The vertex identifier.
     */
    @Override
    public void setId(String id);

    /**
     * Specifies the label of the vertex.
     * 
     * @param label - The vertex label.
     */
    @Override
    public void setLabel(String label);

    /**
     * Obtains the mutable properties of the vertex.
     * 
     * @return The vertex properties.
     */
    @Override
    MutableEpgProperties getProperties();

    /**
     * Specifies the properties of the vertex. A copy might be made if the given
     * properties are immutable.
     * 
     * @param properties - The vertex properties.
     */
    @Override
    public void setProperties(EpgProperties properties);

    /**
     * Obtains the identifiers of the graphs of which the vertex is a member.
     * These are mutable.
     * 
     * @return A (possibly empty) collection of graph identifiers.
     */
    @Override
    Collection<String> getGraphIds();

    /**
     * Specifies the identifiers of the graphs of which the vertex is a member. A
     * copy might be made if the given collection is immutable.
     * 
     * @param graphIds - A (possibly empty) collection of graph identifiers.
     */
    void setGraphIds(Collection<String> graphIds);

}
