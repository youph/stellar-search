package au.com.d2dcrc.ia.search.common;

import java.time.ZoneId;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converters {@link ZoneId} to and from a JPA backend.
 *
 * <p>This will be automatically detected by Hibernate classpath scanning, and thus
 * not required to annotate columns with {@link javax.persistence.Convert}
 */
@Converter(autoApply = true)
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {

    @Override
    public String convertToDatabaseColumn(final ZoneId attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getId();
    }

    @Override
    public ZoneId convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }
        return ZoneId.of(dbData);
    }
}
