package au.com.d2dcrc.ia.search.ingestor.model;

import au.com.d2dcrc.ia.search.ingestor.error.EpgMissingFieldException;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
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
public class EpgVertexModel implements EpgElementModel {

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

    /**
     * Validates that all required vertex fields are present.
     * 
     * @throws EpgMissingFieldException if a vertex field is missing.
     */
    @Override
    public void validate() throws EpgMissingFieldException {
        if (this.getId() == null) {
            throw new EpgMissingFieldException("Missing EPG vertex identifier");
        }
        if (this.getData() == null) {
            throw new EpgMissingFieldException("Missing EPG vertex data");
        }
        if (this.getMetaData() == null) {
            throw new EpgMissingFieldException("Missing EPG vertex meta-data");
        }
        this.getMetaData().validate();
    }

    /**
     * Validates that all fields obey the model schema.
     * 
     * @param validator - The external schema validator.
     * @throws EpgSyntaxException if the model data do not obey the model schema.
     */
    @Override
    public void validate(Validator validator) throws EpgSyntaxException  {
        Set<ConstraintViolation<EpgVertexModel>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            throw new EpgSyntaxException(new ConstraintViolationException(violations).getMessage());
        }
    }
    
}
