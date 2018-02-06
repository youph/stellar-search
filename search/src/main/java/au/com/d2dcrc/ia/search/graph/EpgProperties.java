package au.com.d2dcrc.ia.search.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * Encapsulates the EPG properties as a map.
 */
public class EpgProperties extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs empty EPG properties.
     */
    public EpgProperties() {
        super();
    }

    /**
     * Constructs EPG properties from a shallow copy of the given map.
     * 
     * @param properties - The properties map (of which a shallow copy is made).
     */
    @JsonCreator
    public EpgProperties(Map<String, Object> properties) {
        super();
        for (java.util.Map.Entry<String, Object> entry : properties.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            checkKeyValue(key, value);
            super.put(key, value);
        }
    }

    /**
     * Constructs EPG properties from key/value property pairs.
     * 
     * @param properties - An even-length array of key/value property pairs.
     */
    public EpgProperties(Object... properties) {
        super();
        final int length = properties.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Require pairs of key/value arguments");
        }
        for (int i = 0; i < length;) {
            Object key = properties[i++];
            Object value = properties[i++];
            checkKeyValue(key, value);
            super.put((String) key, value);
        }
    }

    private void checkKey(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Require a non-null key");
        }
        if (!(key instanceof String)) {
            throw new IllegalArgumentException("Require a string key instead of: " + key);
        }
    }

    private void checkKeyValue(Object key, Object value) {
        checkKey(key);
        if (value == null) {
            throw new IllegalArgumentException("Require a non-null value for key: " + key);
        }
    }

    /**
     * Obtains the value of the named property.
     * 
     * @param key - The name of the property.
     * @return The value of the property, or a value of null if the property is undefined.
     */
    public @Nullable Object getProperty(String key) {
        checkKey(key);
        return super.get(key);
    }

    /**
     * Specifies the value of the named property.
     * 
     * @param key - The name of the property.
     * @param value - The non-null value of the property.
     */
    public void setProperty(String key, Object value) {
        checkKeyValue(key, value);
        super.put(key, value);
    }

    /**
     * Obtains the defined property keys.
     * 
     * @return A set of property keys.
     */
    public Set<String> getPropertyKeys() {
        return new HashSet<>(super.keySet());
    }

    /**
     * Indicates the number of defined properties.
     * 
     * @return The number of properties.
     */
    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof EpgProperties)) {
            return false;
        }
        EpgProperties otherProperties = (EpgProperties) other;
        if (this.size() != otherProperties.size()) {
            return false;
        }
        for (Entry<String, Object> entry : super.entrySet()) {
            Object value = otherProperties.getProperty(entry.getKey());
            Object myValue = entry.getValue();
            if (!myValue.equals(value) && !myValue.toString().equals(value.toString())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
