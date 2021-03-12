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

    private static final Config config = ConfigFactory.load("application.properties");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate restTemplate = new RestTemplate();

    private final HttpHeaders httpHeaders = new HttpHeaders();
    private static boolean isLogout = true;

    public HttpStatus save(Prospectable prospectable) {
        //Response response;
        log.info ("About to save {}", prospectable);
        ResponseEntity<LeadData> response = null;
        try {
            if(isLogout){
                login();
            }
            String url = erpnextServerURL + "/api/resource/Lead";
            log.info("Request url: {}",url);

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(prospectable), httpHeaders);

            log.info("request to be sent {}", entity);
            response = restTemplate.exchange(url, HttpMethod.POST, entity, LeadData.class);
            log.info("Request successfull, here is the response:  {}", response);
        } catch (Exception e) {
           log.error("Could not save Prospectable={} due to", prospectable, e);
        } finally {
            logout();
        }
        return response!=null ? response.getStatusCode(): HttpStatus.FAILED_DEPENDENCY;
    }

    private void updateHttpHeaders(List<String> cookies) {
        httpHeaders.set("Cookie", String.join(";", cookies));
    }

    private void login() {
        log.info("login into erpnext...");
        String bodyString = String.format(
                "{\"usr\":\"%s\",\"pwd\":\"%s\"}",
                config.getString("erpNextAccount"), config.getString("erpNextAccountPassword")
        );

        String urlLogin = config.getString("erpnextServerURL") + "/api/method/login";
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(bodyString, httpHeaders);
        ResponseEntity<byte[]> response = restTemplate.exchange(urlLogin, HttpMethod.POST, entity, byte[].class);

        log.info("Sending request for connection opening with following parameters");
        log.info("URL String : {}",urlLogin);
        log.info("Response returned : {}", response);
        HttpHeaders headers = response.getHeaders();
        log.info("headers received {}", headers);
        log.info("Cookies received {}", headers.get(HttpHeaders.SET_COOKIE));
        List<String> cookies = headers.getValuesAsList(HttpHeaders.SET_COOKIE);
        isLogout = false;
        updateHttpHeaders(cookies);
    }

    private void logout(){
        try {
            log.info("logout from erpnext...");
            String urlLogout = config.getString("erpnextServerURL") + "/api/method/logout";
            HttpEntity<String> entityLogout = new HttpEntity<>(httpHeaders);

            ResponseEntity<String> responseLogout = restTemplate.exchange(urlLogout, HttpMethod.GET, entityLogout, String.class);
            log.info("response for logging out {} ", responseLogout);
            isLogout = true;
        } catch (Exception ex){
            log.error("logout failed due to ", ex);
        }
    }
}
