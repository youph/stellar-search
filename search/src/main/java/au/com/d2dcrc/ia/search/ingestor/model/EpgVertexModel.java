package au.com.d2dcrc.ia.search.ingestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Models the JSON representation of an EPG vertex as found in the raw data
 * files.
 */
@ApiModel
@Validated
public class EpgVertexModel {

    @ApiModelProperty(value = "the identifier of the vertex", required = true)
    @NotNull
    private final String id;

    @ApiModelProperty(value = "the properties of the vertex")
    @NotNull
    private final Map<String, Object> data;

    @ApiModelProperty(value = "the graph meta-data of the vertex")
    @NotNull
    @Valid
    private final EpgVertexMetaDataModel metaData;

    /**
     * Constructs a model of an EPG vertex.
     * 
     * @param id - the identifier of the vertex.
     * @param data - the properties of the vertex.
     * @param metaData - the graph meta-data of the vertex.
     */
    @JsonCreator
    public EpgVertexModel(
        @JsonProperty("id") final String id,
        @JsonProperty("data") final Map<String, Object> data,
        @JsonProperty("meta") final EpgVertexMetaDataModel metaData
    ) {
        this.id = id;
        this.data = data;
        this.metaData = metaData;
    }

    /**
     * Obtains the identifier of the vertex.
     * 
     * @return The vertex identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtains the properties of the vertex.
     * 
     * @return The vertex data.
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Obtains the graph meta-data of the vertex.
     * 
     * @return The vertex meta-data.
     */
    public EpgVertexMetaDataModel getMetaData() {
        return metaData;
    }
}
