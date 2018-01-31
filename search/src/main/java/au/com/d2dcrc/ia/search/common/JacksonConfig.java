package au.com.d2dcrc.ia.search.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Configure the object mapper used in spring.
 */
@Configuration
public class JacksonConfig {

    /**
     * Creates an object mapper bean used in spring. We need to make some changes to the default
     * like register the jackson-module-parameter-names (in addition to the jdk8 module that
     * spring auomatically registers). We also want to use the ISO 8601 (datetime) interchange
     * format for rest APIs.
     *
     * @param builder the injected builder for creating the object mapper
     * @return a newly configured object mapper used everywhere in spring
     */
    @Bean
    public ObjectMapper jackson2ObjectMapperBuilder(Jackson2ObjectMapperBuilder builder) {

        return builder.createXmlMapper(false)
            .build()
            // spring only registers well known modules like jdk8 but doesn't register
            // jackson-module-parameter-names module which gives us immutables
            .findAndRegisterModules()
            // Serialise java 8 datetimes as strings in ISO 6801 interchange format
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // Preserve timezone info! - Don't throw away information, if we want to adjust tz we
            // can do so ourselves
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

    }

}
