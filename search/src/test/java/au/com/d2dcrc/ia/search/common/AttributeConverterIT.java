package au.com.d2dcrc.ia.search.common;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.time.Instant;
import java.time.ZoneId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Tests the custom {@link ZoneIdConverter}
 *
 * Mainly based off
 * <a href="https://github.com/spring-projects/spring-data-jpa/blob/1.11.9.RELEASE/src/test/java/org/springframework/data/jpa/convert/threeten/Jsr310JpaConvertersIntegrationTests.java">this</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ZoneIdConverter.class, AttributeConverterIT.Config.class})
@Transactional
public class AttributeConverterIT {

    /**
     * Simple entity to persist with ZoneId
     */
    @Entity
    public static class DateTimeSample {
        @Id
        @GeneratedValue
        private Long id;
        private Instant instant;
        private ZoneId zoneId;
    }

    /**
     * The configuration for the test. This was mainly based off
     * <a href="https://github.com/spring-projects/spring-data-jpa/blob/1.11.9.RELEASE/src/test/java/org/springframework/data/jpa/domain/support/AbstractAttributeConverterIntegrationTests.java">this</a>.
     */
    @TestConfiguration
    public static class Config {

        @Bean
        public DataSource dataSource() {
            // TODO: Have this driven by 'test' profile and application-test.yml
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }

        @Bean
        public FactoryBean<EntityManagerFactory> entityManagerFactory(final DataSource dataSource) {

            final AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            vendorAdapter.setDatabase(Database.H2);
            vendorAdapter.setGenerateDdl(true);

            final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
            factory.setDataSource(dataSource);
            factory.setPackagesToScan(AttributeConverterIT.class.getPackage().getName());
            factory.setJpaVendorAdapter(vendorAdapter);

            return factory;
        }

        @Bean
        public PlatformTransactionManager transactionManager() {
            return new JpaTransactionManager();
        }

    }

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testUseZoneIdConverter() {

        final DateTimeSample sample = new DateTimeSample();

        sample.instant = Instant.now();
        sample.zoneId = ZoneId.of("Europe/Berlin");

        em.persist(sample);
        em.flush();
        em.clear();

        final DateTimeSample result = em.find(DateTimeSample.class, sample.id);

        assertThat(result, is(notNullValue()));
        assertThat(result.instant, is(sample.instant));
        assertThat(result.zoneId, is(sample.zoneId));
    }

}
