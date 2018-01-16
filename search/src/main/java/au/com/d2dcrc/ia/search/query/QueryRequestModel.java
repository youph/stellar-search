package au.com.d2dcrc.ia.search.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Model of a query posted by a user to the Search API.
 */
@ApiModel
@Validated
public class QueryRequestModel {

    @ApiModelProperty
    @NotNull
    @Valid
    private final QueryRequestNode node;

    /**
     * Constructor.
     *
     * @param node the root node query of this model.
     */
    @JsonCreator
    public QueryRequestModel(
        @JsonProperty("node") final QueryRequestNode node
    ) {
        this.node = node;
    }

    public QueryRequestNode getNode() {
        return node;
    }
}
