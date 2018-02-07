package au.com.d2dcrc.ia.search.ingestor.extractor;

import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.graph.EpgVertex;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import au.com.d2dcrc.ia.search.ingestor.model.EpgVertexModel;
import java.io.IOException;

/**
 * Allows the repeated extraction of EPG vertices from JSON strings in
 * {@link EpgVertexModel} format.
 */
public class EpgVertexExtractor extends EpgElementExtractor<EpgVertex> {

    @Override
    public EpgVertex extract(String json) throws EpgSyntaxException {
        try {
            EpgVertexModel model = mapper.readValue(json, EpgVertexModel.class);
            model.validate();
            return new EpgVertex(
                model.getId(),
                model.getMetaData().getLabel(),
                new EpgProperties(model.getData()),
                asSet(model.getMetaData().getGraphIds())
            );
        } catch (IOException e) {
            throw new EpgSyntaxException(e.getMessage());
        }
    }

}
