package ca.aretex.irex.experim.http.auth.factory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
//@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {

    final Config config = ConfigFactory.load("application.properties");
    final String clientId=config.getString("client.id");
    final String clientSecret=config.getString("client.secret");
    final String tokenUrl = config.getString("server.token.url");

    private RestTemplate restTemplate;

    public RestTemplateFactory() {
        super();
    }

    // API

    @Override
    public RestTemplate getObject() {
        return restTemplate;
    }

    @Override
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        HttpHost httpHost = HttpHost.create(tokenUrl);
        log.info("httpHost={}",httpHost);
        final ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost);
        restTemplate = new RestTemplate(requestFactory);
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(clientId, clientSecret));
    }

}
