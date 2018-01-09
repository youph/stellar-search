package au.com.d2dcrc.ia.search.elastic;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties supporting the Elasticsearch rest client.
 *
 * @see org.elasticsearch.client.RestClientBuilder
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch.rest-client")
@Validated
public class RestClientProperties {

    @NotEmpty
    private final List<String> addresses = new ArrayList<>();
    private String pathPrefix;
    private Integer maxRetryTimeout;
    private RequestConfigProperties requestConfig;

    /**
     * List of addresses. Format must be parsable by {@link org.apache.http.HttpHost#create}.
     * Must not be empty.
     *
     * @return list of addresses
     */
    public List<String> getAddresses() {
        return addresses;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public Integer getMaxRetryTimeout() {
        return maxRetryTimeout;
    }

    public RequestConfigProperties getRequestConfig() {
        return requestConfig;
    }

    public void setMaxRetryTimeout(Integer maxRetryTimeout) {
        this.maxRetryTimeout = maxRetryTimeout;
    }

    public void setRequestConfig(RequestConfigProperties requestConfig) {
        this.requestConfig = requestConfig;
    }
}
