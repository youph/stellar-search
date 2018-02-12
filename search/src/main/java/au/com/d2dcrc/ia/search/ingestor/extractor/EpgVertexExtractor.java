package au.com.d2dcrc.ia.search.ingestor.extractor;

import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.graph.EpgVertex;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import au.com.d2dcrc.ia.search.ingestor.model.EpgVertexModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Validator;

/**
 * Allows the repeated extraction of EPG vertices from JSON strings in
 * {@link EpgVertexModel} format.
 */
public class EpgVertexExtractor extends EpgElementExtractor<EpgVertex> {

    /**
     * Initialises the extractor.
     */
    public EpgVertexExtractor() {
        super();
    }

    /**
     * Initialises the extractor.
     * 
     * @param mapper - The JSON object mapper.
     * @param validator - The model schema validator.
     */
    public EpgVertexExtractor(ObjectMapper mapper, Validator validator) {
        super(mapper, validator);
    }

    @Override
    public EpgVertex extract(String json) throws EpgSyntaxException {
        EpgVertexModel model = readModel(json, EpgVertexModel.class);
        return new EpgVertex(
            model.getId(),
            model.getMetaData().getLabel(),
            new EpgProperties(model.getData()),
            asSet(model.getMetaData().getGraphIds())
        );
    }

}
