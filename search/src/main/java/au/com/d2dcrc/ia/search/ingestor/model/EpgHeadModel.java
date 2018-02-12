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
 * Models the JSON representation of an EPG head as found in the raw data files.
 */
@ApiModel
@Validated
public class EpgHeadModel implements EpgElementModel {

    @ApiModelProperty(value = "the identifier of the head", required = true)
    @NotNull
    private final String id;

    @ApiModelProperty(value = "the properties of the head")
    @NotNull
    private final Map<String, Object> data;

    @ApiModelProperty(value = "the graph meta-data of the head")
    @NotNull
    @Valid
    private final EpgHeadMetaDataModel metaData;

    /**
     * Constructs a model of an EPG head.
     * 
     * @param id - the identifier of the head.
     * @param data - the properties of the head.
     * @param metaData - the graph meta-data of the head.
     */
    @JsonCreator
    public EpgHeadModel(
        @JsonProperty("id") final String id,
        @JsonProperty("data") final Map<String, Object> data,
        @JsonProperty("meta") final EpgHeadMetaDataModel metaData
    ) {
        this.id = id;
        this.data = data;
        this.metaData = metaData;
    }

    /**
     * Obtains the identifier of the head.
     * 
     * @return The head identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtains the properties of the head.
     * 
     * @return The head data.
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Obtains the graph meta-data of the head.
     * 
     * @return The head meta-data.
     */
    public EpgHeadMetaDataModel getMetaData() {
        return metaData;
    }

    /**
     * Validates that all required head fields are present.
     * 
     * @throws EpgMissingFieldException if a head field is missing.
     */
    @Override
    public void validate() throws EpgMissingFieldException {
        if (this.getId() == null) {
            throw new EpgMissingFieldException("Missing EPG head identifier");
        }
        if (this.getData() == null) {
            throw new EpgMissingFieldException("Missing EPG head data");
        }
        if (this.getMetaData() == null) {
            throw new EpgMissingFieldException("Missing EPG head meta-data");
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
        Set<ConstraintViolation<EpgHeadModel>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            throw new EpgSyntaxException(new ConstraintViolationException(violations).getMessage());
        }
    }
    
}
