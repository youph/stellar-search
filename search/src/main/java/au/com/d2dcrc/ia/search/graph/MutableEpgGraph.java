package au.com.d2dcrc.ia.search.graph;

/**
 * Defines an EPG graph <em>head</em> that is modifiable.
 */
public interface MutableEpgGraph extends EpgGraph, MutableEpgElement {

    /**
     * Specifies the unique identifier of the graph.
     * 
     * @param id - The graph identifier.
     */
    @Override
    public void setId(String id);

    /**
     * Specifies the label of the graph.
     * 
     * @param label - The graph label.
     */
    @Override
    public void setLabel(String label);

    /**
     * Obtains the mutable properties of the graph.
     * 
     * @return The graph properties.
     */
    @Override
    MutableEpgProperties getProperties();

    /**
     * Specifies the properties of the graph. A copy might be made if the given
     * properties are immutable.
     * 
     * @param properties
     *            - The graph properties.
     */
    @Override
    public void setProperties(EpgProperties properties);

}
