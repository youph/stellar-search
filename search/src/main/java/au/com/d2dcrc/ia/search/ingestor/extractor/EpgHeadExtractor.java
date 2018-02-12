package au.com.d2dcrc.ia.search.ingestor.extractor;

import au.com.d2dcrc.ia.search.graph.EpgHead;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import au.com.d2dcrc.ia.search.ingestor.model.EpgHeadModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Validator;

/**
 * Allows the repeated extraction of EPG heads from JSON strings in
 * {@link EpgHeadModel} format.
 */
public class EpgHeadExtractor extends EpgElementExtractor<EpgHead> {

    /**
     * Initialises the extractor.
     */
    public EpgHeadExtractor() {
        super();
    }

    /**
     * Initialises the extractor.
     * 
     * @param mapper - The JSON object mapper.
     * @param validator - The model schema validator.
     */
    public EpgHeadExtractor(ObjectMapper mapper, Validator validator) {
        super(mapper, validator);
    }

    @Override
    public EpgHead extract(String json) throws EpgSyntaxException {
        EpgHeadModel model = readModel(json, EpgHeadModel.class);
        return new EpgHead(
            model.getId(),
            model.getMetaData().getLabel(),
            new EpgProperties(model.getData())
        );
    }

}
