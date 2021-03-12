package ca.aretex.irex.experim.http.auth;

import ca.aretex.irex.experim.http.auth.factory.HttpComponentsClientHttpRequestFactoryBasicAuth;
import com.typesafe.config.Config;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.springframework.web.client.RestTemplate;

@ToString
@Builder
@AllArgsConstructor
@Getter
@Slf4j
public class BasicClientConfig {
    private final String clientId;
    private final String clientSecret;
    private final String tokenUrl;

    public BasicClientConfig(Config config){
        this(
                config.getString("erpNextAccount"),
                config.getString("erpNextAccountPassword"),
                config.getString("erpnextServerURL")
        );
    }

    public RestTemplate getBasicRestTemplate(HttpHost httpHost){
        return getBasicRestTemplate(httpHost, 0);
    }

    public RestTemplate getBasicRestTemplate(HttpHost httpHost, int timeout){
        HttpComponentsClientHttpRequestFactoryBasicAuth clientHttpRequestFactory = timeout > 0
                ? new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost, clientId, clientSecret, timeout)
                : new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost, clientId, clientSecret);
        return new RestTemplate(clientHttpRequestFactory);
    }

    public RestTemplate getBasicRestTemplate(){
        return getBasicRestTemplate(getHttpHost());
    }

    public HttpHost getHttpHost(){
        HttpHost httpHost = HttpHost.create(tokenUrl);
        log.info("httpHost={}",httpHost);
        return httpHost;
    }
}
