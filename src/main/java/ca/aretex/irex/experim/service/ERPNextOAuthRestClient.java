package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.http.OAuthClient;
import org.springframework.web.client.RestTemplate;

public class ERPNextOAuthRestClient<T> extends OAuthClient<T, String> {
    public ERPNextOAuthRestClient(String serverBaseUrl, RestTemplate restTemplate){
        super(serverBaseUrl, restTemplate, String.class);
    }
}
