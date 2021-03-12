package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.response.LeadData;
import ca.aretex.irex.experim.http.RestClient;
import org.springframework.web.client.RestTemplate;

public class ERPNextRestClient<T> extends RestClient<T, LeadData> {
    public ERPNextRestClient(String serverBaseUrl, RestTemplate restTemplate){
        super(serverBaseUrl, restTemplate, LeadData.class);
    }
}
