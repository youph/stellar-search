package au.com.d2dcrc.ia.search.common;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests the additional converters in the conversion service
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConversionConfig.class)
public class ConversionConfigTest {

    @Inject
    private ConversionService conversionService;

    @Test
    public void testStringToInstant() {
        final Instant result = conversionService.convert("2018-01-07T08:37:19.822Z", Instant.class);
        final Instant expected = Instant.ofEpochSecond(1515314239, 822000000);
        assertEquals(result, expected);
    }

    @Test
    public void testStringToOffsetDateTime() {
        final OffsetDateTime result = conversionService.convert(
            "2018-01-07T19:08:30.011+10:30",
            OffsetDateTime.class
        );
        final OffsetDateTime expected = OffsetDateTime.of(
            2018, 1, 7, 19, 8, 30, 11000000, ZoneOffset.ofHoursMinutes(10, 30)
        );
        assertEquals(result, expected);
    }

    @Test
    public void testStringToZonedDateTime() {
        final ZonedDateTime result = conversionService.convert(
            "2018-01-07T19:08:30.011+10:30[Australia/Adelaide]",
            ZonedDateTime.class
        );
        final ZonedDateTime expected = ZonedDateTime.of(
            2018, 1, 7, 19, 8, 30, 11000000, ZoneId.of("Australia/Adelaide")
        );
        assertEquals(result, expected);
    }

    @Test
    public void testStringToZoneId() {
        assertEquals(
            conversionService.convert("Australia/Adelaide", ZoneId.class),
            ZoneId.of("Australia/Adelaide")
        );
    }

    @Test
    public void testStringToLocalDateTime() {
        assertEquals(
            conversionService.convert("2018-01-07T00:00:00", LocalDateTime.class),
            LocalDateTime.of(2018, 1, 7, 0, 0, 0)
        );
    }

    @Test
    public void testStringToLocalDate() {
        assertEquals(
            conversionService.convert("2018-01-07", LocalDate.class),
            LocalDate.of(2018, 1, 7)
        );
    }

    @Test
    public void testStringToPeriod() {
        assertEquals(
            conversionService.convert("P2Y5M3D", Period.class),
            Period.of(2, 5, 3)
        );
    }

    @Test
    public void testStringToDuration() {
        assertEquals(
            conversionService.convert("P1DT10H17M36.789S", Duration.class),
            Duration.ofMillis(123456789L)
        );
    }

}
