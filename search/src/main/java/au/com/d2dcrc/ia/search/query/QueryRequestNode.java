package au.com.d2dcrc.ia.search.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
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
    private final Map<String, JsonNode> properties;

    @ApiModelProperty(value = "optional node relations")
    private final List<QueryRequestNodeRelation> relations;

    /**
     * Constructor.
     *
     * @param label the label of the node (the node type)
     * @param properties optional properties of the node
     * @param relations a relation (edge) connected to this node
     */
    @JsonCreator
    public QueryRequestNode(
        @JsonProperty("label") final ImdbNodeLabel label,
        @JsonProperty("properties") final Map<String, JsonNode> properties,
        @JsonProperty("relations") final List<QueryRequestNodeRelation> relations
    ) {
        this.label = label;
        this.properties = properties;
        this.relations = relations;
    }

    public ImdbNodeLabel getLabel() {
        return label;
    }

    public Map<String, JsonNode> getProperties() {
        return properties;
    }

    public List<QueryRequestNodeRelation> getRelations() {
        return relations;
    }
}
