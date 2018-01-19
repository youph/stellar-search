package au.com.d2dcrc.ia.search.graph.impl;

import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.graph.MutableEpgProperties;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@JsonSerialize(using = EpgPropertiesImplSerializer.class)
public class EpgPropertiesImpl implements MutableEpgProperties {

    /*package-private*/ /*NotNull*/ Map<String, Object> properties;

    /**
     * Default constructor.
     */
    public EpgPropertiesImpl() {
        properties = new LinkedHashMap<String, Object>();
    }

    /**
     * Properties map reference constructor.
     * 
     * @param properties - The properties map (to which a reference is kept).
     */
    @JsonCreator
    public EpgPropertiesImpl(Map<String, Object> properties) {
        this.properties = (properties != null) ? properties : new LinkedHashMap<String, Object>();
    }

    /**
     * Simple properties constructor.
     * 
     * @param properties - An even-length array of key/value property pairs.
     */
    public EpgPropertiesImpl(Object... properties) {
        this.properties = new LinkedHashMap<String, Object>();
        final int length = properties.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Require pairs of key/value arguments");
        }
        for (int i = 0; i < length;) {
            Object key = properties[i++];
            Object value = properties[i++];
            checkKeyValue(key, value);
            this.properties.put((String) key, value);
        }
    }

    private void checkKeyValue(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Require a non-null key");
        }
        if (!(key instanceof String)) {
            throw new IllegalArgumentException("Require a string key instead of: " + key);
        }
        if (value == null) {
            throw new IllegalArgumentException("Require a non-null value for key: " + key);
        }
    }

    @Override
    public @Nullable Object getProperty(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Require a non-null key");
        }
        return properties.get(key);
    }

    @Override
    public void setProperty(String key, Object value) {
        checkKeyValue(key, value);
        properties.put(key, value);
    }

    @Override
    public int size() {
        return properties.size();
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
        for (Entry<String, Object> entry : properties.entrySet()) {
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
        return properties.hashCode();
    }

    @Override
    public Collection<String> getPropertyKeys() {
        return new ArrayList<>(properties.keySet());
    }

}
