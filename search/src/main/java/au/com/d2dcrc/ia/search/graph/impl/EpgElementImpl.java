package au.com.d2dcrc.ia.search.graph.impl;

import au.com.d2dcrc.ia.search.graph.EpgElement;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.graph.MutableEpgElement;
import au.com.d2dcrc.ia.search.graph.MutableEpgProperties;

public class EpgElementImpl implements MutableEpgElement {

    protected String id;
    protected String label;
    protected MutableEpgProperties properties = new EpgPropertiesImpl();

    /**
     * Default constructor.
     */
    public EpgElementImpl() {
    }

    /**
     * Simple constructor.
     * 
     * @param id - The element identifier.
     * @param label - The element label.
     * @param properties - The element properties.
     */
    protected EpgElementImpl(String id, String label, EpgProperties properties) {
        this.id = id;
        this.label = label;
        setProperties(properties);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public MutableEpgProperties getProperties() {
        return properties;
    }

    @Override
    public void setProperties(EpgProperties properties) {
        if (properties == null) {
            this.properties = new EpgPropertiesImpl();
        } else if (properties instanceof MutableEpgProperties) {
            this.properties = (MutableEpgProperties) properties;
        } else {
            this.properties = new EpgPropertiesImpl();
            for (String key : properties.getPropertyKeys()) {
                this.properties.setProperty(key, properties.getProperty(key));
            }
        }
    }

    @Override
    public int hashCode() {
        int h1 = (id == null) ? 0 : id.hashCode();
        int h2 = (label == null) ? 0 : label.hashCode();
        int h3 = properties.hashCode();
        return 31 * (31 * h1 + h2) + h3;
    }

    @Override
    public boolean equals(Object other) {
        EpgElement otherElem = (other instanceof EpgElement) ? (EpgElement) other : null;
        return otherElem != null
            && match(id, otherElem.getId())
            && match(label, otherElem.getLabel())
            && properties.equals(otherElem.getProperties());
    }

    protected boolean match(String v1, String v2) {
        return v1 == null && v2 == null || v1 != null && v1.equals(v2);
    }

}
