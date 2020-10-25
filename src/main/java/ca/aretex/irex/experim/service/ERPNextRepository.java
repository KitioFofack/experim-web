package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.request.Prospectable;
import ca.aretex.irex.experim.bean.response.LeadData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
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


    RestTemplate restTemplate = getRestTemplate();



    List<String> cookieList = Collections.emptyList();
    public HttpStatus save(Prospectable clt) {
        //Response response;
        log.info ("About to save {}", clt);
        try {
            String url = erpnextServerURL + "/api/resource/Lead";
            log.info("Request url: {}",url);
            //RestTemplate restTemplate = getRestTemplate();
            HttpHeaders httpHeaders = getHttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(clt), httpHeaders);

            log.info("request to be sent {}", entity);
            ResponseEntity<LeadData> response = restTemplate.exchange(url, HttpMethod.POST, entity, LeadData.class);
            log.info("Request successfull, here is the response:  {}", response);
        } catch (Exception e) {
            e.printStackTrace();
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


        if(cookieList.isEmpty()){
            openConnexion();
        }
        httpHeaders.set("Cookie", String.join(";", cookieList));
        return httpHeaders;
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    private void openConnexion() {
        String bodyString = String.format("{\"usr\":\"%s\",\"pwd\":\"%s\"}", erpNextAccount, erpNextAccountPassword);
        HttpHeaders httpHeaders = new HttpHeaders();

        String url = erpnextServerURL + "/api/method/login";

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(bodyString, httpHeaders);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class);

        log.info("Sending request for connection opening with following parameters");
        log.info("URL String : {}",url);
//        log.info("Body String : {}",bodyString);
        log.info("Response returned : {}", response);
        HttpHeaders headers = response.getHeaders();
        log.info("headers received {}", headers);
        log.info("Cookies received {}", headers.get(HttpHeaders.SET_COOKIE));
        cookieList = headers.getValuesAsList(HttpHeaders.SET_COOKIE);
    }
}
