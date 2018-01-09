package au.com.d2dcrc.ia.search.elastic;

import java.util.function.Consumer;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties supporting Elasticsearch.
 */
@Configuration
public class ElasticsearchConfig {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    private static class RequestConfigCallbackImpl implements RequestConfigCallback {

        final RequestConfigProperties props;

        private RequestConfigCallbackImpl(final RequestConfigProperties props) {
            this.props = props;
        }

        @Override
        public RequestConfig.Builder customizeRequestConfig(final RequestConfig.Builder builder) {

            setIfNotNull(props.getAuthenticationEnabled(), builder::setAuthenticationEnabled);
            setIfNotNull(props.getCircularRedirectsAllowed(), builder::setCircularRedirectsAllowed);
            setIfNotNull(props.getConnectionRequestTimeout(), builder::setConnectionRequestTimeout);
            setIfNotNull(props.getConnectTimeout(), builder::setConnectTimeout);
            setIfNotNull(props.getContentCompressionEnabled(), builder::setContentCompressionEnabled);
            setIfNotNull(props.getCookieSpec(), builder::setCookieSpec);
            setIfNotNull(props.getExpectContinueEnabled(), builder::setExpectContinueEnabled);
            setIfNotNull(props.getLocalAddress(), builder::setLocalAddress);
            setIfNotNull(props.getMaxRedirects(), builder::setMaxRedirects);
            setIfNotNull(props.getProxy(), s -> builder.setProxy(HttpHost.create(s)));
            setIfNotNull(props.getProxyPreferredAuthSchemes(), builder::setProxyPreferredAuthSchemes);
            setIfNotNull(props.getRedirectsEnabled(), builder::setRedirectsEnabled);
            setIfNotNull(props.getRelativeRedirectsAllowed(), builder::setRelativeRedirectsAllowed);
            setIfNotNull(props.getSocketTimeout(), builder::setSocketTimeout);
            setIfNotNull(props.getTargetPreferredAuthSchemes(), builder::setTargetPreferredAuthSchemes);

            return builder;
        }

        private static <T> void setIfNotNull(T value, Consumer<T> f) {
            if (value != null) {
                f.accept(value);
            }
        }

    }

    /**
     * Creates a configured Elasticsearch low level REST client builder bean.
     *
     * @param props the rest client properties
     * @return a configured low level rest client builder
     */
    @Bean
    public RestClientBuilder elasticRestClientBuilder(final RestClientProperties props) {

        final HttpHost[] hosts = props.getAddresses()
            .stream()
            .map(HttpHost::create)
            .toArray(HttpHost[]::new);

        final RestClientBuilder restClientBuilder = RestClient.builder(hosts);

        if (props.getPathPrefix() != null) {
            restClientBuilder.setPathPrefix(props.getPathPrefix());
        }

        if (props.getMaxRetryTimeout() != null) {
            restClientBuilder.setMaxRetryTimeoutMillis(props.getMaxRetryTimeout());
        }

        if (props.getRequestConfig() != null) {
            restClientBuilder.setRequestConfigCallback(
                new RequestConfigCallbackImpl(props.getRequestConfig())
            );
        }

        return restClientBuilder;

    }

    /**
     * Creates an Elasticsearch High Level REST client bean.
     *
     * @param builder the low level REST client bean.
     * @return a new High Level REST client
     */
    @Bean
    public RestHighLevelClient elasticHighLevelRestClient(final RestClientBuilder builder) {
        return new RestHighLevelClient(builder);
    }

}
