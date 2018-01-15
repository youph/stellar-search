package au.com.d2dcrc.ia.search.common;

import java.net.URI;
import java.time.ZoneId;
import java.util.function.Function;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A bunch of JPA {@link AttributeConverter}s.
 */
public class JpaConverters {

    /**
     * Converters {@link ZoneId} to and from a JPA backend.
     *
     * <p>This will be automatically detected by Hibernate classpath scanning, and thus
     * not required to annotate columns with {@link javax.persistence.Convert}
     */
    @Converter(autoApply = true)
    public static class ZoneIdConverter implements AttributeConverter<ZoneId, String> {
        @Override
        public String convertToDatabaseColumn(final ZoneId attribute) {
            return attribute != null ? attribute.getId() : null;
        }

        @Override
        public ZoneId convertToEntityAttribute(final String dbData) {
            return dbData != null ? ZoneId.of(dbData) : null;
        }
    }

    /**
     * Converters {@link URI} to and from a JPA backend.
     *
     * <p>This will be automatically detected by Hibernate classpath scanning, and thus
     * not required to annotate columns with {@link javax.persistence.Convert}
     */
    @Converter(autoApply = true)
    public static class UriConverter implements AttributeConverter<URI, String> {
        @Override
        public String convertToDatabaseColumn(final URI attribute) {
            return attribute != null ? attribute.toString() : null;
        }

        @Override
        public URI convertToEntityAttribute(final String dbData) {
            return dbData != null ? URI.create(dbData) : null;
        }
    }

}
