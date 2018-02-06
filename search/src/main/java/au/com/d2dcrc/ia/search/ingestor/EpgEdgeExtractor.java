package au.com.d2dcrc.ia.search.ingestor;

import au.com.d2dcrc.ia.search.graph.EpgEdge;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.ingestor.model.EpgEdgeModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Allows the repeated extraction of EPG edges from textual JSON
 * representations.
 */
public class EpgEdgeExtractor implements EpgElementExtractor<EpgEdge> {

    private final ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public EpgEdge extract(String json) throws EpgSyntaxException {
        try {
            EpgEdgeModel model = mapper.readValue(json, EpgEdgeModel.class);
            return new EpgEdge(
                model.getId(),
                model.getMetaData().getLabel(),
                new EpgProperties(model.getData()),
                model.getSourceId(),
                model.getTargetId(),
                asSet(model.getMetaData().getGraphIds())
            );
        } catch (IOException e) {
            throw new EpgDataException(e.getMessage());
        }
    }

    private Set<String> asSet(String[] idArray) {
        Set<String> idSet = new HashSet<>();
        for (String id : idArray) {
            idSet.add(id);
        }
        return idSet;
    }

}
