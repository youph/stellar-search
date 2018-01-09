package au.com.d2dcrc.ia.search.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests that java datetimes are serialized and deserialised correctly. Also tests that jackson has
 * support for detecting constructor and factory method ("creator") parameters without having to use @JsonProperty
 * annotation.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class JacksonTest {

    /**
     * Example entity for serialising / deserialising
     */
    public static class Example {

        private final String name;
        private final OffsetDateTime dateTime;

        public Example(final String name, final OffsetDateTime dateTime) {
            this.name = name;
            this.dateTime = dateTime;
        }

        public String getName() {
            return name;
        }

        public OffsetDateTime getDateTime() {
            return dateTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Example example = (Example) o;

            if (name != null ? !name.equals(example.name) : example.name != null) {
                return false;
            }
            return dateTime != null ? dateTime.equals(example.dateTime) : example.dateTime == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
            return result;
        }
    }

    private final Example fixture = new Example(
        "hello",
        OffsetDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneOffset.ofHoursMinutes(10, 30))
    );
    private final ClassPathResource jsonResource = new ClassPathResource("example.json", getClass());

    @Inject
    private JacksonTester<Example> json;

    @Test
    public void testSerialize() throws Exception {
        assertThat(this.json.write(this.fixture)).isEqualToJson(this.jsonResource.getPath());
    }

    @Test
    public void testDeserialize() throws Exception {
        assertThat(this.json.read(this.jsonResource)).isEqualTo(this.fixture);
        assertThat(this.json.readObject(this.jsonResource)).isEqualTo(this.fixture);
    }

}