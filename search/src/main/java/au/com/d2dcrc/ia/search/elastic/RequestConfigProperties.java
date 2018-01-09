package au.com.d2dcrc.ia.search.elastic;

import java.net.InetAddress;
import java.util.List;

/**
 * Configuration properties supporting Apache HTTP {@link org.apache.http.client.config.RequestConfig},
 * used by the elasticsearch REST client.
 *
 * @see org.apache.http.client.config.RequestConfig.Builder
 */
public class RequestConfigProperties {

    private String cookieSpec;
    private Boolean authenticationEnabled;
    private Boolean expectContinueEnabled;
    private String proxy;
    private InetAddress localAddress;
    private Boolean redirectsEnabled;
    private Boolean relativeRedirectsAllowed;
    private Boolean circularRedirectsAllowed;
    private Integer maxRedirects;
    private List<String> targetPreferredAuthSchemes;
    private List<String> proxyPreferredAuthSchemes;
    private Integer connectionRequestTimeout;
    private Integer connectTimeout;
    private Integer socketTimeout;
    private Boolean contentCompressionEnabled;

    public String getCookieSpec() {
        return cookieSpec;
    }

    public void setCookieSpec(String cookieSpec) {
        this.cookieSpec = cookieSpec;
    }

    public Boolean getAuthenticationEnabled() {
        return authenticationEnabled;
    }

    public void setAuthenticationEnabled(Boolean authenticationEnabled) {
        this.authenticationEnabled = authenticationEnabled;
    }

    public Boolean getExpectContinueEnabled() {
        return expectContinueEnabled;
    }

    public void setExpectContinueEnabled(Boolean expectContinueEnabled) {
        this.expectContinueEnabled = expectContinueEnabled;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public InetAddress getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(InetAddress localAddress) {
        this.localAddress = localAddress;
    }

    public Boolean getRedirectsEnabled() {
        return redirectsEnabled;
    }

    public void setRedirectsEnabled(Boolean redirectsEnabled) {
        this.redirectsEnabled = redirectsEnabled;
    }

    public Boolean getRelativeRedirectsAllowed() {
        return relativeRedirectsAllowed;
    }

    public void setRelativeRedirectsAllowed(Boolean relativeRedirectsAllowed) {
        this.relativeRedirectsAllowed = relativeRedirectsAllowed;
    }

    public Boolean getCircularRedirectsAllowed() {
        return circularRedirectsAllowed;
    }

    public void setCircularRedirectsAllowed(Boolean circularRedirectsAllowed) {
        this.circularRedirectsAllowed = circularRedirectsAllowed;
    }

    public Integer getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(Integer maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public List<String> getTargetPreferredAuthSchemes() {
        return targetPreferredAuthSchemes;
    }

    public void setTargetPreferredAuthSchemes(List<String> targetPreferredAuthSchemes) {
        this.targetPreferredAuthSchemes = targetPreferredAuthSchemes;
    }

    public List<String> getProxyPreferredAuthSchemes() {
        return proxyPreferredAuthSchemes;
    }

    public void setProxyPreferredAuthSchemes(List<String> proxyPreferredAuthSchemes) {
        this.proxyPreferredAuthSchemes = proxyPreferredAuthSchemes;
    }

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Boolean getContentCompressionEnabled() {
        return contentCompressionEnabled;
    }

    public void setContentCompressionEnabled(Boolean contentCompressionEnabled) {
        this.contentCompressionEnabled = contentCompressionEnabled;
    }

}
