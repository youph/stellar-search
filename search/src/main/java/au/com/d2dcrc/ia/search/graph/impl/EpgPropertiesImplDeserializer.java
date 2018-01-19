package au.com.d2dcrc.ia.search.graph.impl;

import au.com.d2dcrc.ia.search.graph.EpgProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@SuppressWarnings("serial")
public class EpgPropertiesImplDeserializer extends StdDeserializer<EpgProperties> {

    public EpgPropertiesImplDeserializer() {
        this(null);
    }

    public EpgPropertiesImplDeserializer(Class<EpgProperties> t) {
        super(t);
    }

    @Override
    public EpgProperties deserialize(
        JsonParser jp, 
        DeserializationContext dc
    ) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        return extractProperties(node);
    }

    /**
     * Extracts the EPG properties from an already-parsed JSON node.
     * 
     * @param jsonProperties - The JSON properties node.
     * @return The EPG properties.
     * @throws JsonProcessingException If the JSON node is not a valid object.
     */
    public static EpgProperties extractProperties(JsonNode jsonProperties) throws JsonProcessingException {
        JsonNodeType nodeType = jsonProperties.getNodeType();
        if (nodeType != JsonNodeType.OBJECT) {
            throw new JsonProcessingException("Require a JSON array of graph identifiers") {};
        }
        EpgPropertiesImpl properties = new EpgPropertiesImpl();
        for (Iterator<Entry<String, JsonNode>> iter = jsonProperties.fields(); iter.hasNext();) {
            Entry<String, JsonNode> field = iter.next();
            properties.setProperty(field.getKey(), extractValue(field.getValue()));
        }
        return properties;
    }

    // TODO Handle more complex values.
    private static @Nullable Object extractValue(JsonNode valueNode) {
        switch (valueNode.getNodeType()) {
            case BOOLEAN:
                return valueNode.booleanValue();
            case NUMBER:
                return extractNumber(valueNode);
            case STRING:
                return valueNode.asText();
            case ARRAY:
            case BINARY:
            case MISSING:
            case NULL:
            case OBJECT:
            case POJO:
            default:
                return null;
        }
    }

    private static Object extractNumber(JsonNode valueNode) {
        if (valueNode.isIntegralNumber()) {
            if (valueNode.isInt()) {
                return valueNode.intValue();
            } else if (valueNode.isLong()) {
                return valueNode.longValue();
            } else if (valueNode.isShort()) {
                return valueNode.shortValue();
            } else if (valueNode.isBigInteger()) {
                return valueNode.bigIntegerValue();
            }
        } else if (valueNode.isFloatingPointNumber()) {
            if (valueNode.isDouble()) {
                return valueNode.doubleValue();
            } else if (valueNode.isFloat()) {
                return valueNode.floatValue();
            } else if (valueNode.isBigDecimal()) {
                return valueNode.decimalValue();
            }
        }

        throw new IllegalArgumentException("Failed to deserialise number: " + valueNode.asText());
    }

}