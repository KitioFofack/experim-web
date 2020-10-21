package ca.aretex.irex.experim.http.auth;

import ca.aretex.irex.experim.http.auth.factory.HttpComponentsClientHttpRequestFactoryBasicAuth;
import com.typesafe.config.Config;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
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
                config.getString("client.id"),
                config.getString("client.secret"),
                config.getString("server.token.url")
        );
    }

    public RestTemplate getBasicRestTemplate(HttpHost httpHost){
        return getBasicRestTemplate(httpHost, 0);
    }

    public RestTemplate getBasicRestTemplate(HttpHost httpHost, int timeout){
        HttpComponentsClientHttpRequestFactoryBasicAuth requestFactory = timeout > 0
                ? new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost, timeout)
                : new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(clientId, clientSecret));
        return restTemplate;
    }

    public RestTemplate getBasicRestTemplate(){
        return getBasicRestTemplate(getHttpHost());
    }

    public HttpHost getHttpHost(){
        String[] fields = StringUtils.splitByWholeSeparatorPreserveAllTokens(tokenUrl.toLowerCase(), ":", 3);

        String scheme = fields[0];
        int defaultPort;
        switch (scheme){
            case "http":
                defaultPort = 80;
                break;
            case "https":
                defaultPort =  443;
                break;
            default:
                defaultPort = 8080;
                break;
        }

        String hostname = fields[1].replaceFirst("//", "").split("/")[0];

        int port = defaultPort;
        if(fields.length > 2 && fields[2] != null  && !fields[2].isEmpty()){
            try{
                port = Integer.parseInt(fields[2].split("/")[0]);
            } catch (Exception ex){
                log.warn("could not read port={} from tokenUrl={} due to...", port, tokenUrl, ex);
            }
        }
        return new HttpHost(hostname, port, scheme);
    }
}
