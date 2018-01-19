package au.com.d2dcrc.ia.search.graph;

import java.util.Collection;
import javax.annotation.Nullable;

/**
 * Defines key/value properties for a graph, vertex or edge.
 */
public interface EpgProperties {

    /**
     * Obtains the value of the named property.
     * 
     * @param key - The name of the property.
     * @return The value of the property, or a value of null if the property is undefined.
     */
    public @Nullable Object getProperty(String key);

    /**
     * Indicates the number of defined properties.
     * 
     * @return The number of properties.
     */
    public int size();

    /**
     * Obtains the defined property keys.
     * 
     * @return A collection of property keys.
     */
    public Collection<String> getPropertyKeys();

}
