package au.com.d2dcrc.ia.search;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Base class for spring unit and integration tests. This will enable the 'test' profile
 * configuration for these tests.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public abstract class BaseSpringTest {

}
