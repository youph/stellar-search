package au.com.d2dcrc.ia.search.ingestor.model;

import au.com.d2dcrc.ia.search.ingestor.error.EpgMissingFieldException;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import javax.validation.Validator;

/**
 * Specifies the data schema of the EPG element.
 */
public interface EpgElementModel {

    /**
     * Validates that all required element fields are present.
     * 
     * @throws EpgMissingFieldException if a head field is missing.
     */
    void validate() throws EpgMissingFieldException;

    /**
     * Validates that all element fields obey the model schema.
     * 
     * @param validator - The external schema validator.
     * @throws EpgSyntaxException if the model data do not obey the model schema.
     */
    void validate(Validator validator) throws EpgSyntaxException;

}
