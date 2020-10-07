package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.Candidate;
import ca.aretex.irex.experim.bean.Client;
import ca.aretex.irex.experim.bean.Partner;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ERPNextService {

    private static Logger logger= LoggerFactory.getLogger(ERPNextService.class);
    @Value("${erpnextInstance}")
    private String erpnextServerURL;

    @Value("${erpnextAccount}")
    private String erpnextAccount;

    @Value("${erpnextPassword}")
    private String erpnextPassword;

    public HttpStatus save(Client clt) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            okhttp3.MediaType mediaType = MediaType.parse("application/json");

            okhttp3.RequestBody body = RequestBody.create(mediaType, ""+clt);
            okhttp3.Request request = new Request.Builder()
                    .url("https://capetc-dev.irex.aretex.ca/api/resource/Lead")
                    .method("POST", body)
                    .addHeader("Authorization", "token 6b751d8f5c688cb:88ace929ef10d55")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", "sid=Guest")
                    .build();

            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpStatus.CREATED;
    }
}
