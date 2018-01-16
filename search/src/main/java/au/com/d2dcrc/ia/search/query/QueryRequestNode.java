package au.com.d2dcrc.ia.search.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Node part of a query posted by a user to the Search API.
 */
@ApiModel
@Validated
public class QueryRequestNode {

    @ApiModelProperty(value = "the label of the node", required = true)
    @NotNull
    private final ImdbNodeLabel label;

    @ApiModelProperty(value = "optional node properties")
    private final Map<String, Object> properties;

    @ApiModelProperty(value = "optional node relations")
    private final QueryRequestNodeRelation relation;

    /**
     * Constructor.
     *
     * @param label the label of the node (the node type)
     * @param properties optional properties of the node
     * @param relation a relation (edge) connected to this node
     */
    @JsonCreator
    public QueryRequestNode(
        @JsonProperty("label") final ImdbNodeLabel label,
        @JsonProperty("properties") final Map<String, Object> properties,
        @JsonProperty("relation") final QueryRequestNodeRelation relation
    ) {
        this.label = label;
        this.properties = properties;
        this.relation = relation;
    }

    public ImdbNodeLabel getLabel() {
        return label;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public QueryRequestNodeRelation getRelation() {
        return relation;
    }
}
