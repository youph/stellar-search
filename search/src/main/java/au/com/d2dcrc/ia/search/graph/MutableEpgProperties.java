package au.com.d2dcrc.ia.search.graph;

/**
 * Defines EPG properties that are modifiable.
 */
public interface MutableEpgProperties extends EpgProperties {

    /**
     * Specifies the value of the named property.
     * 
     * @param key
     *            - The name of the property.
     * @param value
     *            - The non-null value of the property.
     */
    void setProperty(String key, Object value);

}
