package au.com.d2dcrc.ia.search.ingestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Models the JSON representation of EPG vertex meta-data as found in the raw data
 * files.
 */
@ApiModel
public class EpgVertexMetaDataModel {

    @ApiModelProperty(value = "the label of the vertex", required = true)
    @NotNull
    private final String label;

    @ApiModelProperty(value = "the graph identifiers of the vertex", required = true)
    @NotNull
    private final String[] graphIds;

    /**
     * Constructs a model of the EPG vertex meta-data.
     * 
     * @param label - the label of the vertex.
     * @param graphIds - the graph identifiers of the vertex.
     */
    @JsonCreator
    public EpgVertexMetaDataModel(
        @JsonProperty("label") final String label,
        @JsonProperty("graphs") final String[] graphIds
    ) {
        this.label = label;
        this.graphIds = graphIds;
    }

    /**
     * Obtains the label of the vertex.
     * 
     * @return The vertex label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Obtains the graph identifiers of the vertex.
     * 
     * @return The graph identifiers.
     */
    public String[] getGraphIds() {
        return graphIds;
    }

}
