package au.com.d2dcrc.ia.search.graph.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import au.com.d2dcrc.ia.search.graph.EpgEdge;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.Collections;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests that the {@link EpgEdge} object is correctly serialised into JSON and
 * deserialised from JSON.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class EpgEdgeImplTest {

    private final EpgEdgeImpl fixture = new EpgEdgeImpl(
        "342D5869102440778B69FDF03756C850",
        "isRelatedTo",
        new EpgPropertiesImpl("relationship", "sibling"),
        "342D5869102440778B69FDF03756C853",
        "342D5869102440778B69FDF03756C851",
        Collections.singleton("342D5869102440778B69FDF03756C858")
    );

    private final ClassPathResource jsonResource = new ClassPathResource("edge.json", getClass());

    @Inject
    private JacksonTester<EpgEdgeImpl> json;

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
        EpgEdge vertex1 = mapper.reader().forType(EpgEdgeImpl.class).readValue(json);
        assertEquals(fixture, vertex1);
        SimpleModule mod = new SimpleModule();
        mod.addDeserializer(EpgEdge.class, new EpgEdgeImplDeserializer());
        mapper.registerModule(mod);
        EpgEdge vertex2 = mapper.reader().forType(EpgEdge.class).readValue(json);
        assertEquals(fixture, vertex2);
    }

}