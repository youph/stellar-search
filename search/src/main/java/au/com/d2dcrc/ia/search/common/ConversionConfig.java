package au.com.d2dcrc.ia.search.common;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * Configuration for the conversion service.
 */
@Configuration
public class ConversionConfig {

    /**
     * Allows converting application string properties into target java.time classes automatically. Note this
     * has nothing to do with Jackson's support for java 8 times since Spring doesn't use jackson-dataformat-yaml
     * for deserialising YAML properties (and since not all properties are provided through YAML config.
     *
     * <p>See <a href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-conversion">spring docs</a>
     *
     * @return configured conversion service bean
     */
    @Bean
    public ConversionService conversionService() {

        final ConfigurableConversionService conversionService = new DefaultConversionService();

        conversionService.addConverter(String.class, Instant.class, Instant::parse);
        conversionService.addConverter(String.class, OffsetDateTime.class, OffsetDateTime::parse);
        conversionService.addConverter(String.class, ZonedDateTime.class, ZonedDateTime::parse);
        conversionService.addConverter(String.class, LocalDateTime.class, LocalDateTime::parse);
        conversionService.addConverter(String.class, LocalDate.class, LocalDate::parse);
        conversionService.addConverter(String.class, Period.class, Period::parse);
        conversionService.addConverter(String.class, Duration.class, Duration::parse);
        conversionService.addConverter(String.class, ZoneId.class, ZoneId::of);

        return conversionService;

    }

}
