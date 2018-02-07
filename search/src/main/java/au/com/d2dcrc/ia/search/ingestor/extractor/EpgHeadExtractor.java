package au.com.d2dcrc.ia.search.ingestor.extractor;

import au.com.d2dcrc.ia.search.graph.EpgHead;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import au.com.d2dcrc.ia.search.ingestor.model.EpgHeadModel;
import java.io.IOException;

/**
 * Allows the repeated extraction of EPG heads from JSON strings in
 * {@link EpgHeadModel} format.
 */
public class EpgHeadExtractor extends EpgElementExtractor<EpgHead> {

    @Override
    public EpgHead extract(String json) throws EpgSyntaxException {
        try {
            EpgHeadModel model = mapper.readValue(json, EpgHeadModel.class);
            model.validate();
            return new EpgHead(
                model.getId(),
                model.getMetaData().getLabel(),
                new EpgProperties(model.getData())
            );
        } catch (IOException e) {
            throw new EpgSyntaxException(e.getMessage());
        }
    }

}
