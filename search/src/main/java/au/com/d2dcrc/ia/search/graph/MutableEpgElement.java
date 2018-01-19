package au.com.d2dcrc.ia.search.graph;

/**
 * Defines an EPG element that is modifiable.
 */
public interface MutableEpgElement extends EpgElement {

    /**
     * Specifies the unique identifier of the element.
     * 
     * @param id - The element identifier.
     */
    public void setId(String id);

    /**
     * Specifies the label of the element.
     * 
     * @param label - The element label.
     */
    public void setLabel(String label);

    /**
     * Obtains the mutable properties of the element.
     * 
     * @return The element properties.
     */
    @Override
    MutableEpgProperties getProperties();

    /**
     * Specifies the properties of the element. A copy might be made if the given
     * properties are immutable.
     * 
     * @param properties
     *            - The element properties.
     */
    public void setProperties(EpgProperties properties);

}
