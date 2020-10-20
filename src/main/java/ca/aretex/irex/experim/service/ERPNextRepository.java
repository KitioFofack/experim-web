package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.Client;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
//import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.http.HttpClient;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ERPNextRepository {

    private static Logger logger = LoggerFactory.getLogger(ERPNextRepository.class);

    private static HttpClient client;
    //private static CloseableHttpClient httpClient;

    @Value("${erpnextServerURL}")
    private String erpnextServerURL;

    @Value("${erpNextAccount}")
    private String erpNextAccount;

    @Value("${erpNextAccountPassword}")
    private String erpNextAccountPassword;


    List<Cookie> cookieList;
    public HttpStatus save(Client clt) {
        //Response response;
        logger.info ("About to save {}", clt);
        try {
            if (client == null) {
                logger.info("Client unexistant, we have to instanciate one");
                openConnexion();
            }
            //MediaType mediaType = MediaType.parse("application/json");

            //RequestBody body = RequestBody.create( "" + clt, mediaType);

            String url = new StringBuilder().append(erpnextServerURL).append("/api/resource/Lead").toString();

            logger.info("Request url: {}",url);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Cookie", cookieList.stream()
                    .map(c -> c.toString())
                    .collect(Collectors.joining(";")));

            HttpEntity<String> entity = new HttpEntity<>(""+clt, httpHeaders);

            /*logger.info("Data request using cookie string  : {}", cookieList.stream()
                    .map(c -> c.toString())
                    .collect(Collectors.joining(";")));
            logger.info("request to be sent {}", entity);*/


            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            logger.info("Here it still working my negger !!! ");
            logger.info("Request successfull, here is the response:  {}", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpStatus.CREATED;
    }

    private void openConnexion() throws IOException {
        //client = new OkHttpClient().newBuilder().build();
        client = HttpClient.newBuilder().build();
        //okhttp3.MediaType mediaType = MediaType.parse("application/json");
        String bodyString = "{\"usr\":\"kfofack@irex.aretex.ca\",\"pwd\":\"vtgn3fdh\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();


        //okhttp3.RequestBody body = RequestBody.create( "" + bodyString, mediaType);

        String url = new StringBuilder().append(erpnextServerURL).append("/api/method/login").toString();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("" + bodyString, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        logger.info("Sending request for connection opening with following parameters");
        logger.info("URL String : {}",url);
        logger.info("Body Sring : {}",bodyString);
        logger.info("Response returned : {}", response);
        HttpHeaders headers = response.getHeaders();
        //Headers headers1 = response.headers();
        logger.info("headers received {}", headers);
        //List<Cookie> cookies = Collections.singletonList(Cookie.parse(HttpUrl.parse(erpnextServerURL), headers.toString()));
        //List<Cookie> cookies = Collections.singletonList(Cookie.parse(HttpUrl.parse(erpnextServerURL), headers.toString()));
        //logger.info("Cookies received {}", cookies);
        //cookieList = cookies;
    }
}
