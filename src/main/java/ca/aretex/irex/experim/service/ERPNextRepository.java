package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.Client;
import ca.aretex.irex.experim.http.auth.OAuth2ClientConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ERPNextRepository {

    private static HttpClient client;

    @Value("${erpnextServerURL}")
    private String erpnextServerURL;

    @Value("${erpNextAccount}")
    private String erpNextAccount;
    private final Config config = ConfigFactory.load("application.properties");
    private final OAuth2ClientConfig oAuth2ClientConfig = new OAuth2ClientConfig(config);
    private final ERPNextOAuthRestClient<Client> client = new ERPNextOAuthRestClient<>(
            oAuth2ClientConfig.getTokenUrl(),
            oAuth2ClientConfig.getOAuth2RestTemplate()
    );

    @Value("${erpNextAccountPassword}")
    private String erpNextAccountPassword;


    List<String> cookieList;
    public HttpStatus save(Client clt) {
        //Response response;
        log.info ("About to save {}", clt);
        try {
            if (client == null) {
                log.info("Client unexistant, we have to instanciate one");
                openConnexion();
            }
            String url = new StringBuilder().append(erpnextServerURL).append("/api/resource/Lead").toString();
            log.info("Request url: {}",url);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Cookie", cookieList.stream()
                    .map(c -> c.toString())
                    .collect(Collectors.joining(";")));

            HttpEntity<String> entity = new HttpEntity<>(""+clt, httpHeaders);

            log.info("Data request using cookie string  : {}", cookieList.stream()
                    .map(c -> c.toString())
                    .collect(Collectors.joining(";")));
            log.info("request to be sent {}", entity);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            log.info("Request successfull, here is the response:  {}", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpStatus.CREATED;
    }

    private void openConnexion() throws IOException {
        client = HttpClient.newBuilder().build();
        String bodyString = "{\"usr\":\"kfofack@irex.aretex.ca\",\"pwd\":\"vtgn3fdh\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        String url = new StringBuilder().append(erpnextServerURL).append("/api/method/login").toString();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("" + bodyString, httpHeaders);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class);

        log.info("Sending request for connection opening with following parameters");
        log.info("URL String : {}",url);
        log.info("Body Sring : {}",bodyString);
        log.info("Response returned : {}", response);
        HttpHeaders headers = response.getHeaders();
        log.info("headers received {}", headers);
        log.info("Cookies received {}", headers.get(HttpHeaders.SET_COOKIE));
        cookieList = headers.getValuesAsList(HttpHeaders.SET_COOKIE);
        return client.apply(clt).map(str -> HttpStatus.CREATED).orElse(HttpStatus.PRECONDITION_FAILED);
    }
}
