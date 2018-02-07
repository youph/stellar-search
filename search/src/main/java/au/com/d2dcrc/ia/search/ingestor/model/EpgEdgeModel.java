package au.com.d2dcrc.ia.search.ingestor.model;

import au.com.d2dcrc.ia.search.ingestor.error.EpgMissingFieldException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Models the JSON representation of an EPG edge as found in the raw data files.
 */
@ApiModel
@Validated
public class EpgEdgeModel {

    @ApiModelProperty(value = "the identifier of the edge", required = true)
    @NotNull
    private final String id;

    @ApiModelProperty(value = "the identifier of the source vertex", required = true)
    @NotNull
    private final String sourceId;

    @ApiModelProperty(value = "the identifier of the target vertex", required = true)
    @NotNull
    private final String targetId;

    @ApiModelProperty(value = "the properties of the edge")
    @NotNull
    private final Map<String, Object> data;

    @ApiModelProperty(value = "the graph meta-data of the edge")
    @NotNull
    @Valid
    private final EpgEdgeMetaDataModel metaData;

    /**
     * Constructs a model of an EPG edge.
     * 
     * @param id - the identifier of the edge.
     * @param sourceId - the identifier of the source vertex.
     * @param targetId - the identifier of the target vertex.
     * @param data - the properties of the edge.
     * @param metaData - the graph meta-data of the edge.
     */
    @JsonCreator
    public EpgEdgeModel(
        @JsonProperty("id") final String id,
        @JsonProperty("source") final String sourceId,
        @JsonProperty("target") final String targetId,
        @JsonProperty("data") final Map<String, Object> data,
        @JsonProperty("meta") final EpgEdgeMetaDataModel metaData
    ) {
        this.id = id;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.data = data;
        this.metaData = metaData;
    }

    /**
     * Obtains the identifier of the edge.
     * 
     * @return The edge identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtains the identifier of the source vertex.
     * 
     * @return The source identifier.
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Obtains the identifier of the target vertex.
     * 
     * @return The target identifier.
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * Obtains the properties of the edge.
     * 
     * @return The edge data.
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Obtains the graph meta-data of the edge.
     * 
     * @return The edge meta-data.
     */
    public EpgEdgeMetaDataModel getMetaData() {
        return metaData;
    }

    /**
     * Validates that all required edge fields are present.
     * 
     * @throws EpgMissingFieldException if an edge field is missing.
     */
    public void validate() throws EpgMissingFieldException {
        if (this.getId() == null) {
            throw new EpgMissingFieldException("Missing EPG edge identifier");
        }
        if (this.getSourceId() == null) {
            throw new EpgMissingFieldException("Missing EPG edge source identifier");
        }
        if (this.getTargetId() == null) {
            throw new EpgMissingFieldException("Missing EPG edge target identifier");
        }
        if (this.getData() == null) {
            throw new EpgMissingFieldException("Missing EPG edge data");
        }
        if (this.getMetaData() == null) {
            throw new EpgMissingFieldException("Missing EPG edge meta-data");
        }
        this.getMetaData().validate();
    }

}
