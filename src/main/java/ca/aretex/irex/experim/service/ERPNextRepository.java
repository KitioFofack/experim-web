package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.request.Prospectable;
import ca.aretex.irex.experim.bean.response.LeadData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Repository
public class ERPNextRepository {

    @Value("${erpnextServerURL}")
    private String erpnextServerURL;

    @Value("${erpNextAccount}")
    private String erpNextAccount;

    @Value("${erpNextAccountPassword}")
    private String erpNextAccountPassword;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate restTemplate = new RestTemplate();;
    private static final List<String> cookies = getAuthCookies();

    public HttpStatus save(Prospectable prospectable) {
        //Response response;
        log.info ("About to save {}", prospectable);
        try {
            String url = erpnextServerURL + "/api/resource/Lead";
            log.info("Request url: {}",url);
            HttpHeaders httpHeaders = getHttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(prospectable), httpHeaders);

            log.info("request to be sent {}", entity);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            log.info("Request successfull, here is the response:  {}", response);
        } catch (Exception e) {
           log.error("Could not save Prospectable={} due to", prospectable, e);
        }
        return HttpStatus.CREATED;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

//        String auth = erpNextAccount + ":" + erpNextAccountPassword;
//        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
//        String authHeader = "Basic " + new String( encodedAuth );
//        httpHeaders.set( "Authorization", authHeader );

        httpHeaders.set("Cookie", String.join(";", cookies));
        return httpHeaders;
    }

    private static List<String> getAuthCookies() {
        Config config = ConfigFactory.load("application.properties");
        String bodyString = String.format(
                "{\"usr\":\"%s\",\"pwd\":\"%s\"}",
                config.getString("erpNextAccount"), config.getString("erpNextAccountPassword")
        );
        HttpHeaders httpHeaders = new HttpHeaders();

        String url = config.getString("erpnextServerURL") + "/api/method/login";

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(bodyString, httpHeaders);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class);

        log.info("Sending request for connection opening with following parameters");
        log.info("URL String : {}",url);
        log.info("Response returned : {}", response);
        HttpHeaders headers = response.getHeaders();
        log.info("headers received {}", headers);
        log.info("Cookies received {}", headers.get(HttpHeaders.SET_COOKIE));
        return headers.getValuesAsList(HttpHeaders.SET_COOKIE);
    }
}
