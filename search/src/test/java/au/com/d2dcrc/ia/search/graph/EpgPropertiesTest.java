package au.com.d2dcrc.ia.search.graph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests that the {@link EpgProperties} object is correctly serialised into JSON
 * and deserialised from JSON.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class EpgPropertiesTest {

    private final EpgProperties fixture = new EpgProperties(
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
    private JacksonTester<EpgProperties> json;

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
    public void testSerializeDeserialize() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fixture);
        EpgProperties properties = mapper.reader().forType(EpgProperties.class).readValue(json);
        assertEquals(fixture, properties);
    }

    @Test
    public void testMethods() throws Exception {
        EpgProperties properties = new EpgProperties();
        properties.setProperty("string", "value1");
        properties.setProperty("int", 42);
        properties.setProperty("boolean", true);
        properties.setProperty("double", 315.0);
        properties.setProperty("long", 2147483700L);
        properties.setProperty("int-as-long", 13L);
        properties.setProperty("biginteger", new BigInteger("92233720368547758070"));
        assertEquals(fixture, properties);
        assertEquals(fixture.getProperty("string"), properties.getProperty("string"));
        assertEquals(fixture.getProperty("int"), properties.getProperty("int"));
        assertEquals(fixture.getProperty("boolean"), properties.getProperty("boolean"));
        assertEquals(fixture.getProperty("double"), properties.getProperty("double"));
        assertEquals(fixture.getProperty("long"), properties.getProperty("long"));
        assertEquals(fixture.getProperty("int-as-long"), properties.getProperty("int-as-long"));
        assertEquals(fixture.getProperty("biginteger"), properties.getProperty("biginteger"));
        assertEquals(fixture.getProperty("no-key"), properties.getProperty("no-key"));
        assertEquals(fixture.getPropertyKeys(), properties.getPropertyKeys());
        assertEquals(fixture.hashCode(), properties.hashCode());
        assertTrue(!properties.equals(null));

        properties.setProperty("int-as-long", 12L);
        assertFalse(fixture.equals(properties));
        properties.setProperty("int-as-long", fixture.getProperty("int-as-long"));
        assertTrue(fixture.equals(properties));
        properties.setProperty("extra", false);
        assertFalse(fixture.equals(properties));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadConstruction() throws Exception {
        @SuppressWarnings("unused")
        EpgProperties properties = new EpgProperties("string", "value1", "int");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullKey() throws Exception {
        @SuppressWarnings("unused")
        EpgProperties properties = new EpgProperties(null, "value1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonStringKey() throws Exception {
        @SuppressWarnings("unused")
        EpgProperties properties = new EpgProperties(42, "value1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullValue() throws Exception {
        @SuppressWarnings("unused")
        EpgProperties properties = new EpgProperties("key1", null);
    }

}