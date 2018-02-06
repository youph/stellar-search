package au.com.d2dcrc.ia.search.graph;

/**
 * Defines a mutable EPG element.
 */
public abstract class EpgElement {

    protected String id;
    protected String label;
    protected EpgProperties properties = new EpgProperties();

    /**
     * Default constructor.
     */
    public EpgElement() {
    }

    /**
     * Simple constructor.
     * 
     * @param id - The identifier.
     * @param label - The label.
     * @param properties - The properties.
     */
    protected EpgElement(String id, String label, EpgProperties properties) {
        this.id = id;
        this.label = label;
        setProperties(properties);
    }

    /**
     * Obtains the unique identifier.
     * 
     * @return The identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Specifies the unique identifier.
     * 
     * @param id - The identifier.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtains the label.
     * 
     * @return The label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Specifies the label.
     * 
     * @param label - The label.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * /** Obtains the mutable properties.
     * 
     * @return The properties.
     */
    public EpgProperties getProperties() {
        return properties;
    }

    /**
     * Specifies the properties.
     * 
     * @param properties - The properties (to which a reference is kept).
     */
    public void setProperties(EpgProperties properties) {
        this.properties = (properties == null) ? new EpgProperties() : properties;
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
