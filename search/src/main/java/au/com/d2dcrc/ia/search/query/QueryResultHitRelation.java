package au.com.d2dcrc.ia.search.query;

/**
 * A node relation to return in a query result.
 */
public class QueryResultHitRelation {
    private final String id;

    private final ImdbRelationLabel label;

    private final String source;

    private final String target;

    /**
     * Constructor.
     *
     * @param id the id of the relation
     * @param label the label of the relation
     * @param source the source of the relation
     * @param target the target of the relation
     */
    public QueryResultHitRelation(
        String id,
        ImdbRelationLabel label,
        String source,
        String target
    ) {
        this.id = id;
        this.label = label;
        this.source = source;
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public ImdbRelationLabel getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }
}
