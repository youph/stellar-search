package au.com.d2dcrc.ia.search.graph.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class EpgPropertiesImplSerializer extends StdSerializer<EpgPropertiesImpl> {
    
    public EpgPropertiesImplSerializer() {
        this(null);
    }
   
    public EpgPropertiesImplSerializer(Class<EpgPropertiesImpl> t) {
        super(t);
    }
 
    @Override
    public void serialize(
        EpgPropertiesImpl value, 
        JsonGenerator gen, SerializerProvider provider
    ) throws IOException {
        gen.writeStartObject();
        for (Entry<String, Object> entry : value.properties.entrySet()) {
            write(gen, entry.getKey(), entry.getValue());
        }
        gen.writeEndObject();
    }

    private void write(JsonGenerator gen, String key, Object value) throws IOException {
        if (value instanceof String) {
            gen.writeStringField(key, (String) value);
        } else if (value instanceof Double) {
            gen.writeNumberField(key, (double) value);
        } else if (value instanceof Long) {
            gen.writeNumberField(key, (long) value);
        } else if (value instanceof Integer) {
            gen.writeNumberField(key, (int) value);
        } else if (value instanceof Boolean) {
            gen.writeBooleanField(key, (boolean) value);
        } else if (value instanceof Float) {
            gen.writeNumberField(key, (float) value);
        } else if (value instanceof Short) {
            gen.writeNumberField(key, (short) value);
        } else if (value instanceof BigInteger) {
            gen.writeFieldName(key);
            gen.writeNumber((BigInteger) value);
        } else if (value instanceof BigDecimal) {
            gen.writeNumberField(key, (BigDecimal) value);
        } else {
            throw new IOException("Failed to JSON serialise key: " + key + " with value: " + value);
        }
    }

}