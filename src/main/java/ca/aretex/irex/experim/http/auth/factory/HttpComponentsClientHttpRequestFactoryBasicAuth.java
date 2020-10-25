package ca.aretex.irex.experim.http.auth.factory;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

public class HttpComponentsClientHttpRequestFactoryBasicAuth extends HttpComponentsClientHttpRequestFactory {

    HttpHost host;

    public HttpComponentsClientHttpRequestFactoryBasicAuth(HttpHost host, String clientId, String clientSecret) {
        this(httpClientBuilder(clientId, clientSecret).build(), host);
    }

    public HttpComponentsClientHttpRequestFactoryBasicAuth(HttpHost host, String clientId, String clientSecret, int timeout) {
        this(
                httpClientBuilder(clientId, clientSecret)
                        .setDefaultRequestConfig(
                                RequestConfig.custom()
                                        .setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout)
                                        .build()
                        )
                        .build(),
                host
        );
    }

    public HttpComponentsClientHttpRequestFactoryBasicAuth(HttpClient httpClient, HttpHost host) {
        super(httpClient);
        this.host = host;
    }

    public HttpComponentsClientHttpRequestFactoryBasicAuth(HttpHost host) {
        super();
        this.host = host;
    }

    private static HttpClientBuilder httpClientBuilder(String clientId, String clientSecret) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(clientId, clientSecret));
        return HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider);
    }

    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
        //HttpContext httpContext = super.createHttpContext(httpMethod, uri);
        return getOrUpdateHttpContext(null);
    }

    private HttpContext getOrUpdateHttpContext(HttpContext httpContext) {
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);
        if (httpContext==null){
            httpContext = new BasicHttpContext();
        }
        httpContext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
        return httpContext;
    }
}
