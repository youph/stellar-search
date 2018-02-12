package au.com.d2dcrc.ia.search.ingestor.extractor;

import au.com.d2dcrc.ia.search.graph.EpgEdge;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import au.com.d2dcrc.ia.search.ingestor.model.EpgEdgeModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Validator;

/**
 * Allows the repeated extraction of EPG edges from JSON strings in
 * {@link EpgEdgeModel} format.
 */
public class EpgEdgeExtractor extends EpgElementExtractor<EpgEdge> {

    /**
     * Initialises the extractor.
     */
    public EpgEdgeExtractor() {
        super();
    }

    /**
     * Initialises the extractor.
     * 
     * @param mapper - The JSON object mapper.
     * @param validator - The model schema validator.
     */
    public EpgEdgeExtractor(ObjectMapper mapper, Validator validator) {
        super(mapper, validator);
    }

    @Override
    public EpgEdge extract(String json) throws EpgSyntaxException {
        EpgEdgeModel model = readModel(json, EpgEdgeModel.class);
        return new EpgEdge(
            model.getId(),
            model.getMetaData().getLabel(),
            new EpgProperties(model.getData()),
            model.getSourceId(),
            model.getTargetId(),
            asSet(model.getMetaData().getGraphIds())
        );
    }

}
