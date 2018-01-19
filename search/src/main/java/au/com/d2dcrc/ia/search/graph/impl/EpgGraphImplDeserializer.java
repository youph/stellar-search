package au.com.d2dcrc.ia.search.graph.impl;

import au.com.d2dcrc.ia.search.graph.EpgGraph;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@SuppressWarnings("serial")
public class EpgGraphImplDeserializer extends StdDeserializer<EpgGraph> {

    public EpgGraphImplDeserializer() {
        this(null);
    }

    public EpgGraphImplDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EpgGraph deserialize(
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
        return new EpgGraphImpl(id, label, properties);
    }

}