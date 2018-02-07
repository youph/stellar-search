package au.com.d2dcrc.ia.search.ingestor.extractor;

import au.com.d2dcrc.ia.search.graph.EpgEdge;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import au.com.d2dcrc.ia.search.ingestor.model.EpgEdgeModel;
import java.io.IOException;

/**
 * Allows the repeated extraction of EPG edges from JSON strings in
 * {@link EpgEdgeModel} format.
 */
public class EpgEdgeExtractor extends EpgElementExtractor<EpgEdge> {

    @Override
    public EpgEdge extract(String json) throws EpgSyntaxException {
        try {
            EpgEdgeModel model = mapper.readValue(json, EpgEdgeModel.class);
            model.validate();
            return new EpgEdge(
                model.getId(),
                model.getMetaData().getLabel(),
                new EpgProperties(model.getData()),
                model.getSourceId(),
                model.getTargetId(),
                asSet(model.getMetaData().getGraphIds())
            );
        } catch (IOException e) {
            throw new EpgSyntaxException(e.getMessage());
        }
    }

}
