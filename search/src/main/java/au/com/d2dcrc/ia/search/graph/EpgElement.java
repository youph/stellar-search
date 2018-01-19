package au.com.d2dcrc.ia.search.graph;

/**
 * Defines the common interface of a graph, vertex or edge.
 */
public interface EpgElement {

    /**
     * Obtains the unique identifier of the element.
     * 
     * @return The element identifier.
     */
    String getId();

    /**
     * Obtains the label of the element.
     * 
     * @return The element label.
     */
    String getLabel();

    /**
     * Obtains the properties of the element. These should be treated as
     * immutable.
     * 
     * @return The element properties.
     */
    EpgProperties getProperties();

}
