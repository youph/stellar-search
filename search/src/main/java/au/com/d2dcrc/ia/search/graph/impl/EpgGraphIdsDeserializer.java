package au.com.d2dcrc.ia.search.graph.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Enable deserialisation of a JSON array of graph identifiers.
 */
public abstract class EpgGraphIdsDeserializer {

    /**
     * Hide the constructor.
     */
    private EpgGraphIdsDeserializer() {
    }

    /**
     * Extracts a collection of graph identifiers from a JSON array node.
     * 
     * @param graphsNode - The JSON node.
     * @return The collection of graph identifiers.
     * @throws JsonProcessingException If the JSON node is not a valid array of strings.
     */
    @SuppressWarnings("serial")
    public static Collection<String> extractGraphIds(JsonNode graphsNode) throws JsonProcessingException {
        JsonNodeType nodeType = graphsNode.getNodeType();
        if (nodeType != JsonNodeType.ARRAY) {
            throw new JsonProcessingException("Require a JSON array of graph identifiers") {};
        }
        Set<String> graphIds = new HashSet<>();
        for (Iterator<JsonNode> iter = graphsNode.elements(); iter.hasNext();) {
            JsonNode valueNode = iter.next();
            String value = valueNode.asText();
            if (value == null || value.isEmpty()) {
                throw new JsonProcessingException("Require a valid graph identifier") {};
            }
            graphIds.add(value);
        }
        return graphIds;
    }

}
