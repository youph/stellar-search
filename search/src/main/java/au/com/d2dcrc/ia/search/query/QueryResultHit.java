package au.com.d2dcrc.ia.search.query;

import java.util.List;
import java.util.Map;

/**
 * A single hit (node) to return in a query result.
 */
public class QueryResultHit {

    private final float score;

    private final ImdbNodeLabel label;

    private final String id;

    private final Map<String, Object> properties;

    private final List<QueryResultHitRelation> relations;

    /**
     * Constructor.
     *
     * @param score the score of the hit (node)
     * @param label the label of the node
     * @param id the id of the node
     * @param properties properties of the node
     * @param relations relations of the node
     */
    public QueryResultHit(
        float score,
        ImdbNodeLabel label,
        String id,
        Map<String, Object> properties,
        List<QueryResultHitRelation> relations
    ) {
        this.score = score;
        this.label = label;
        this.id = id;
        this.properties = properties;
        this.relations = relations;
    }

    public float getScore() {
        return score;
    }

    public ImdbNodeLabel getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public List<QueryResultHitRelation> getRelations() {
        return relations;
    }
}
