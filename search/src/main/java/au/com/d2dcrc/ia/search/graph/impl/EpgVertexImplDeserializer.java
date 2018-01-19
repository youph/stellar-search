package au.com.d2dcrc.ia.search.graph.impl;

import au.com.d2dcrc.ia.search.graph.EpgProperties;
import au.com.d2dcrc.ia.search.graph.EpgVertex;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Collection;

@SuppressWarnings("serial")
public class EpgVertexImplDeserializer extends StdDeserializer<EpgVertex> {

    public EpgVertexImplDeserializer() {
        this(null);
    }

    public EpgVertexImplDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EpgVertex deserialize(
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
        JsonNode graphsNode = node.get("graphs");
        Collection<String> graphIds = (graphsNode == null) ? null : EpgGraphIdsDeserializer.extractGraphIds(graphsNode);
        return new EpgVertexImpl(id, label, properties, graphIds);
    }

}