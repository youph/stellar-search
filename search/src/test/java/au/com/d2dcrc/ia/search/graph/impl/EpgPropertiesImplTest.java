package au.com.d2dcrc.ia.search.graph.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import au.com.d2dcrc.ia.search.graph.EpgGraph;
import au.com.d2dcrc.ia.search.graph.EpgProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests that the {@link EpgGraph} object is correctly serialised into JSON and
 * deserialised from JSON.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class EpgPropertiesImplTest {

    private final EpgPropertiesImpl fixture = new EpgPropertiesImpl(
        "string", "value1",
        "int", 42,
        "boolean", true,
        "double", 315.0,
        "long", 2147483700L,
        "int-as-long", 13L,
        "biginteger", new BigInteger("92233720368547758070")
    );

    private final ClassPathResource jsonResource = new ClassPathResource("properties.json", getClass());

    @Inject
    private JacksonTester<EpgPropertiesImpl> json;

    @Test
    public void testSerialize() throws Exception {
        assertThat(this.json.write(this.fixture)).isEqualToJson(this.jsonResource.getPath());
    }

    @Test
    public void testDeserialize() throws Exception {
        assertThat(this.json.read(this.jsonResource)).isEqualTo(this.fixture);
        assertThat(this.json.readObject(this.jsonResource)).isEqualTo(this.fixture);
    }

    @Test
    public void testSerializeDeserailize() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fixture);
        EpgProperties properties1 = mapper.reader().forType(EpgPropertiesImpl.class).readValue(json);
        assertEquals(fixture, properties1);
        SimpleModule mod = new SimpleModule();
        mod.addDeserializer(EpgProperties.class, new EpgPropertiesImplDeserializer());
        mapper.registerModule(mod);
        EpgProperties properties2 = mapper.reader().forType(EpgProperties.class).readValue(json);
        assertEquals(fixture, properties2);
    }

}