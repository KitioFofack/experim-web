package ca.aretex.irex.experim.service;

import ca.aretex.irex.experim.bean.Candidate;
import ca.aretex.irex.experim.bean.Partner;
import lombok.extern.slf4j.Slf4j;
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

    public HttpStatus save(Partner partner) {
        logger.info("Saving {} in backend {}", partner, erpnextServerURL );
        return HttpStatus.CREATED;
    }
}
