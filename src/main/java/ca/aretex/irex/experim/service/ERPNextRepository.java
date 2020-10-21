package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.request.Prospectable;
import ca.aretex.irex.experim.http.auth.BasicClientConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ERPNextRepository {

    @Value("${erpnextServerURL}")
    private String erpnextServerURL;


    private final Config config = ConfigFactory.load("application.properties");
    private final BasicClientConfig basicClientConfig = new BasicClientConfig(config);

    public HttpStatus save(Prospectable clt) {
        String erpnextServerPostLeadURL = erpnextServerURL + "/api/resource/Lead";
        ERPNextRestClient<Prospectable> client = new ERPNextRestClient<>(
                erpnextServerPostLeadURL,
                basicClientConfig.getBasicRestTemplate()
        );
        return client.apply(clt).map(str -> HttpStatus.CREATED).orElse(HttpStatus.PRECONDITION_FAILED);
    }
}
