package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.Client;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ERPNextRepository {

    private static OkHttpClient client;

    @Value("${erpnextServerURL}")
    private String erpnextServerURL;

    @Value("${erpNextAccount}")
    private String erpNextAccount;

    @Value("${erpNextAccountPassword}")
    private String erpNextAccountPassword;

    List<Cookie> cookieList;
    public HttpStatus save(Client clt) {
        Response response;
        log.info ("About to save {}", clt);
        try {
            if (client == null) {
                log.info("Client eunexistant, we have to instanciate one");
                openConnexion();
            }
            MediaType mediaType = MediaType.parse("application/json");

            RequestBody body = RequestBody.create( "" + clt, mediaType);

            String url = new StringBuilder().append(erpnextServerURL).append("/api/resource/Lead").toString();

            log.info("Request url: {}",url);

            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
//                    .addHeader("Authorization", token)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", cookieList.stream()
                            .map(c -> c.toString())
                            .collect(Collectors.joining(";")))
                    .build();
            log.info("Data request using cookie string  : {}", cookieList.stream()
                    .map(c -> c.toString())
                    .collect(Collectors.joining(";")));
            log.info("request to be sent {}", request);
//            logger.info("sending request with following parameter token: {} , remote service Endpoint: {}, ", token, erpnextServerURL);
            response = client.newCall(request).execute();
            log.info("Request successfull, here is the response:  {}", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpStatus.CREATED;
    }

    private void openConnexion() throws IOException {

        client = new OkHttpClient().newBuilder().build();

        okhttp3.MediaType mediaType = MediaType.parse("application/json");

        String bodyString = "{\"usr\":\"kfofack@irex.aretex.ca\",\"pwd\":\"vtgn3fdh\"}";

        okhttp3.RequestBody body = RequestBody.create( "" + bodyString, mediaType);

        String url = new StringBuilder().append(erpnextServerURL).append("/api/method/login").toString();

        log.info("Sending request for connection opening with following parameters");
        log.info("URL String : {}",url);
        log.info("Body Sring : {}",bodyString);
        okhttp3.Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        log.info("request to be sent for authentication {}", request);
        Response response = client.newCall(request).execute();
        log.info("Response returned : {}", response);
        Headers headers= response.headers();
        log.info("headers received {}", headers);
        List<Cookie> cookies = Cookie.parseAll(HttpUrl.parse(erpnextServerURL), headers);
        log.info("Cookies received {}", cookies);
        cookieList=cookies;
    }
}
