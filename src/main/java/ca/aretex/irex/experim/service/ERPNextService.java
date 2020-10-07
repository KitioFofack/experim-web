package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.Candidate;
import ca.aretex.irex.experim.bean.Employeurs;
import lombok.extern.slf4j.Slf4j;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.Response;

import java.io.IOException;


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

    public HttpStatus save(Candidate candidate) {
        logger.info("Saving {} in backend {}", candidate, erpnextServerURL );
        return HttpStatus.CREATED;
    }

    public HttpStatus save(Employeurs employeurs)  {
        logger.info("Saving {} in backend {}", employeurs, erpnextServerURL );
        test(employeurs);
        return HttpStatus.CREATED;
    }


    public void test(Employeurs employeurs)
    {
        try {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        String content_body = String.format("{\"enterprise_name\":\"%s\",\"conctact_name\":\"%s\",\"email\":\"%s\",\"phone_number\":\"%s\"}",employeurs.getNom_entreprise(),employeurs.getNom_du_contact(),employeurs.getNumero_de_telephone(),employeurs.getEmail());


        RequestBody body = RequestBody.create(mediaType, content_body);
        Request request = new Request.Builder()
                .url("https://capetc-dev.irex.aretex.ca/api/resource/employeur")
                .method("POST", body)
                .addHeader("Authorization", "token e684f0894ba56f4:db0839db1de6c10")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "sid=Guest")
                .build();

            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
