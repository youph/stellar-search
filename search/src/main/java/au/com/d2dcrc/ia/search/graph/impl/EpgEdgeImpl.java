package au.com.d2dcrc.ia.search.graph.impl;

import au.com.d2dcrc.ia.search.graph.EpgEdge;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.graph.MutableEpgEdge;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@JsonDeserialize(using = EpgEdgeImplDeserializer.class)
public class EpgEdgeImpl extends EpgElementImpl implements MutableEpgEdge {

    @JsonProperty("source")
    private String sourceId;

    @JsonProperty("target")
    private String targetId;

    @JsonProperty("graphs")
    private Set<String> graphIds = new HashSet<>();

    /**
     * Default constructor.
     */
    public EpgEdgeImpl() {
        super();
    }

    /**
     * Simple constructor.
     * 
     * @param id - The graph identifier.
     * @param label - The graph label.
     * @param properties - The graph properties.
     * @param sourceId - The source vertex identifier.
     * @param targetId - The target vertex identifier.
     * @param graphs - The graph identifiers.
     */
    @JsonCreator
    public EpgEdgeImpl(
        String id, 
        String label, 
        EpgProperties properties,
        String sourceId,
        String targetId,
        Collection<String> graphs
    ) {
        super(id, label, properties);
        this.sourceId = sourceId;
        this.targetId = targetId;
        setGraphIds(graphs);
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    @Override
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String getTargetId() {
        return targetId;
    }

    @Override
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public Collection<String> getGraphIds() {
        return graphIds;
    }

    @Override
    public void setGraphIds(Collection<String> graphIds) {
        this.graphIds = (graphIds == null) ? new HashSet<>() : new HashSet<>(graphIds);
    }

    @Override
    public int hashCode() {
        int h1 = (sourceId == null) ? 0 : sourceId.hashCode();
        int h2 = (targetId == null) ? 0 : targetId.hashCode();
        return 31 * (31 * (31 * super.hashCode() + graphIds.hashCode()) + h1) + h2;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof EpgEdge)) {
            return false;
        }
        EpgEdge otherEdge = (EpgEdge) other;
        return match(sourceId, otherEdge.getSourceId())
            && match(targetId, otherEdge.getTargetId())
            && super.equals(otherEdge)
            && graphIds.equals(otherEdge.getGraphIds());
    }

}
