package au.com.d2dcrc.ia.search.ingestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Models the JSON representation of EPG head meta-data as found in the raw data
 * files.
 */
@ApiModel
public class EpgHeadMetaDataModel {

    @ApiModelProperty(value = "the label of the vertex", required = true)
    @NotNull
    private final String label;

    /**
     * Constructs a model of the EPG vertex meta-data.
     * 
     * @param label - the label of the vertex.
     */
    @JsonCreator
    public EpgHeadMetaDataModel(@JsonProperty("label") final String label) {
        this.label = label;
    }

    /**
     * Obtains the label of the vertex.
     * 
     * @return The vertex label.
     */
    public String getLabel() {
        return label;
    }

}
