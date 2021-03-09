package ca.aretex.irex.experim.controler;

import ca.aretex.irex.experim.bean.request.data.Candidate;
import ca.aretex.irex.experim.bean.request.data.Employeur;
import ca.aretex.irex.experim.bean.request.data.Mentor;
import ca.aretex.irex.experim.bean.request.data.Partner;
import ca.aretex.irex.experim.service.ERPNextRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class SubmitController {

    @Autowired
    private ERPNextRepository backendService;
    ResponseEntity responseEntity;

    @PostMapping("/submitCandidate")
    public String submitCandidate(@RequestBody Candidate candidate){
        log.info("Processing request to save a new Candidate");
        responseEntity = ResponseEntity.status(backendService.save(candidate)).build();
        log.info("response entity : {}",responseEntity);
        return "redirect:/Confirmation_d_inscription.html";
    }

    @PostMapping("/submitEmployeur")
    public String submitEmployeur(@RequestBody Employeur employeur){
        log.info("Processing request to save a new Employer");
        responseEntity = ResponseEntity.status(backendService.save(employeur)).build();
        log.info("response entity : {}",responseEntity);
        return "redirect:/Confirmation_d_inscription.html";
    }

    @PostMapping("/submitPartner")
    public String submitPartner(@RequestBody Partner partner) {
        log.info("Processing request to save a new partner");
        responseEntity = ResponseEntity.status(backendService.save(partner)).build();
        log.info("response entity : {}",responseEntity);
        return "redirect:/Confirmation_d_inscription.html";
    }

    @PostMapping("/submitMentor")
    public String submitMentor(@RequestBody Mentor mentor) {
        log.info("Processing request to save a new Mentor");
        responseEntity = ResponseEntity.status(backendService.save(mentor)).build();
        log.info("response entity : {}",responseEntity);
        return "redirect:/Confirmation_d_inscription.html";
    }
}
