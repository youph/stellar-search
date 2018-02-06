package au.com.d2dcrc.ia.search.ingestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Models the JSON representation of EPG edge meta-data as found in the raw data
 * files.
 */
@ApiModel
public class EpgEdgeMetaDataModel {

    @ApiModelProperty(value = "the label of the edge", required = true)
    @NotNull
    private final String label;

    @ApiModelProperty(value = "the graph identifiers of the edge", required = true)
    @NotNull
    private final String[] graphIds;

    /**
     * Constructs a model of the EPG edge meta-data.
     * 
     * @param label - the label of the edge.
     * @param graphIds - the graph identifiers of the edge.
     */
    @JsonCreator
    public EpgEdgeMetaDataModel(
        @JsonProperty("label") final String label,
        @JsonProperty("graphs") final String[] graphIds
    ) {
        this.label = label;
        this.graphIds = graphIds;
    }

    /**
     * Obtains the label of the edge.
     * 
     * @return The edge label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Obtains the graph identifiers of the edge.
     * 
     * @return The graph identifiers.
     */
    public String[] getGraphIds() {
        return graphIds;
    }

}
