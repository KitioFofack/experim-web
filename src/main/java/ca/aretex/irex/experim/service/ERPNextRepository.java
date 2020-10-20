package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.Client;
import ca.aretex.irex.experim.http.auth.OAuth2ClientConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class ERPNextRepository {
    private final Config config = ConfigFactory.load("application.properties");
    private final OAuth2ClientConfig oAuth2ClientConfig = new OAuth2ClientConfig(config);
    private final ERPNextOAuthRestClient<Client> client = new ERPNextOAuthRestClient<>(
            oAuth2ClientConfig.getTokenUrl(),
            oAuth2ClientConfig.getOAuth2RestTemplate()
    );

    public HttpStatus save(Client clt) {
        return client.apply(clt).map(str -> HttpStatus.CREATED).orElse(HttpStatus.PRECONDITION_FAILED);
    }
}
