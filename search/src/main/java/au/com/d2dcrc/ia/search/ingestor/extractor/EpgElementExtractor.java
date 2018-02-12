package au.com.d2dcrc.ia.search.ingestor.extractor;

import au.com.d2dcrc.ia.search.graph.EpgElement;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import au.com.d2dcrc.ia.search.ingestor.model.EpgElementModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Validator;

/**
 * Allows the repeated extraction of EPG elements of the specified type from raw
 * JSON data models.
 *
 * @param <T> The type of EPG element to extract.
 */
public abstract class EpgElementExtractor<T extends EpgElement> {

    private final ObjectMapper mapper;
    private final Validator validator;

    /**
     * Initialises the extractor.
     */
    protected EpgElementExtractor() {
        this(new ObjectMapper(), null);
    }

    /**
     * Initialises the extractor.
     * 
     * @param mapper - The JSON object mapper.
     * @param validator - The model schema validator.
     */
    protected EpgElementExtractor(ObjectMapper mapper, Validator validator) {
        this.mapper = mapper;
        this.validator = validator;
    }

    /**
     * Extracts an EPG element of the pre-specified type from the raw JSON model data.
     * 
     * @param json - The textual JSON representation of the EPG element model.
     * @return The EPG element.
     * @throws EpgSyntaxException if the JSON representation does not obey the EPG element syntax.
     */
    public abstract T extract(String json) throws EpgSyntaxException;

    protected Set<String> asSet(String[] idArray) {
        Set<String> idSet = new HashSet<>();
        for (String id : idArray) {
            idSet.add(id);
        }
        return idSet;
    }

    /**
     * Extracts an EPG model object from raw data.
     * 
     * @param <S> - The type of model.
     * @param json - The textual JSON representation of the EPG element.
     * @param modelClass - The class of the extracted model.
     * @return The model of the EPG element.
     * @throws EpgSyntaxException if the JSON representation does not obey the EPG element syntax.
     */
    protected <S extends EpgElementModel> S readModel(String json, Class<S> modelClass) throws EpgSyntaxException {
        try {
            S model = mapper.readValue(json, modelClass);
            if (validator != null) {
                model.validate(validator);
            } else {
                model.validate();
            }
            return model;
        } catch (IOException e) {
            throw new EpgSyntaxException(e.getMessage());
        }
    }

}
