package au.com.d2dcrc.ia.search.ingestor.extractor;

import au.com.d2dcrc.ia.search.graph.EpgElement;
import au.com.d2dcrc.ia.search.ingestor.error.EpgSyntaxException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;

/**
 * Allows the repeated extraction of EPG elements of the specified type from raw
 * JSON data models.
 *
 * @param <T> The type of EPG element to extract.
 */
public abstract class EpgElementExtractor<T extends EpgElement> {

    protected final ObjectMapper mapper = new ObjectMapper();

    protected EpgElementExtractor() {}

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

}
