package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.http.RestClient;
import org.springframework.web.client.RestTemplate;

public class ERPNextRestClient<T> extends RestClient<T, String> {
    public ERPNextRestClient(String serverBaseUrl, RestTemplate restTemplate){
        super(serverBaseUrl, restTemplate, String.class);
    }
}
