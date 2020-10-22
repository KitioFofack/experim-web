package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.Client;
import lombok.extern.slf4j.Slf4j;
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

    private static Logger logger = LoggerFactory.getLogger(ERPNextRepository.class);

    private static HttpClient client;

    @Value("${erpnextServerURL}")
    private String erpnextServerURL;

    @Value("${erpNextAccount}")
    private String erpNextAccount;

    @Value("${erpNextAccountPassword}")
    private String erpNextAccountPassword;


    List<String> cookieList;
    public HttpStatus save(Client clt) {
        //Response response;
        logger.info ("About to save {}", clt);
        try {
            if (client == null) {
                logger.info("Client unexistant, we have to instanciate one");
                openConnexion();
            }
            String url = new StringBuilder().append(erpnextServerURL).append("/api/resource/Lead").toString();
            logger.info("Request url: {}",url);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Cookie", cookieList.stream()
                    .map(c -> c.toString())
                    .collect(Collectors.joining(";")));

            HttpEntity<String> entity = new HttpEntity<>(""+clt, httpHeaders);

            logger.info("Data request using cookie string  : {}", cookieList.stream()
                    .map(c -> c.toString())
                    .collect(Collectors.joining(";")));
            logger.info("request to be sent {}", entity);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            logger.info("Request successfull, here is the response:  {}", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpStatus.CREATED;
    }

    private void openConnexion() throws IOException {
        client = HttpClient.newBuilder().build();
        String bodyString = "{\"usr\":\"bngameni@irex.aretex.ca\",\"pwd\":\"Password@1234\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        String url = new StringBuilder().append(erpnextServerURL).append("/api/method/login").toString();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("" + bodyString, httpHeaders);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class);

        logger.info("Sending request for connection opening with following parameters");
        logger.info("URL String : {}",url);
        logger.info("Body Sring : {}",bodyString);
        logger.info("Response returned : {}", response);
        HttpHeaders headers = response.getHeaders();
        logger.info("headers received {}", headers);
        logger.info("Cookies received {}", headers.get(HttpHeaders.SET_COOKIE));
        cookieList = headers.getValuesAsList(HttpHeaders.SET_COOKIE);
    }
}
