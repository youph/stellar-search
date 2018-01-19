package au.com.d2dcrc.ia.search.graph.impl;

import au.com.d2dcrc.ia.search.graph.EpgEdge;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Collection;

@SuppressWarnings("serial")
public class EpgEdgeImplDeserializer extends StdDeserializer<EpgEdge> {

    public EpgEdgeImplDeserializer() {
        this(null);
    }

    public EpgEdgeImplDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EpgEdge deserialize(
        JsonParser jp, 
        DeserializationContext ctxt
    ) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode idNode = node.get("id");
        String id = (idNode == null) ? null : idNode.asText();
        JsonNode labelNode = node.get("label");
        String label = (labelNode == null) ? null : labelNode.asText();
        JsonNode propsNode = node.get("properties");
        EpgProperties properties = (propsNode == null) 
                ? null : EpgPropertiesImplDeserializer.extractProperties(propsNode);
        JsonNode sourceNode = node.get("source");
        String sourceId = (sourceNode == null) ? null : sourceNode.asText();
        JsonNode targetNode = node.get("target");
        String targetId = (targetNode == null) ? null : targetNode.asText();
        JsonNode graphsNode = node.get("graphs");
        Collection<String> graphIds = (graphsNode == null) ? null : EpgGraphIdsDeserializer.extractGraphIds(graphsNode);
        return new EpgEdgeImpl(id, label, properties, sourceId, targetId, graphIds);
    }

}