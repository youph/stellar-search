package au.com.d2dcrc.ia.search.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * Relation (edge) part of a query posted by a user to the Search API.
 */
@ApiModel
public class QueryRequestNodeRelation {

    private final RelationDirection direction;
    private final ImdbRelationLabel label;
    private final QueryRequestNode node;

    /**
     * Constructor.
     *
     * @param direction the direction of the relation (in or out)
     * @param label the label of the relation
     * @param node the node connected to this relation (not the query root node)
     */
    @JsonCreator
    public QueryRequestNodeRelation(
        @JsonProperty("direction") final RelationDirection direction,
        @JsonProperty("label") final ImdbRelationLabel label,
        @JsonProperty("node") final QueryRequestNode node
    ) {
        this.direction = direction;
        this.label = label;
        this.node = node;
    }

    public RelationDirection getDirection() {
        return direction;
    }

    public ImdbRelationLabel getLabel() {
        return label;
    }

    public QueryRequestNode getNode() {
        return node;
    }
}
