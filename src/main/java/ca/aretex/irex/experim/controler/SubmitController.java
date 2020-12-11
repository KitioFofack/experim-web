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
    @PostMapping("/submitCandidate")
    public ResponseEntity submitCandidate(@RequestBody Candidate candidate){
        log.info("Processing request to save a new Candidate");
        return ResponseEntity.status(backendService.save(candidate)).build();
    }

    @PostMapping("/submitEmployeur")
    public ResponseEntity submitEmployeur(@RequestBody Employeur employeur){
        log.info("Processing request to save a new Employer");
        return ResponseEntity.status(backendService.save(employeur)).build();
    }

    @PostMapping("/submitPartner")
    public ResponseEntity submitPartner(@RequestBody Partner partner) {
        log.info("Processing request to save a new partner");
        ResponseEntity responseEntity = ResponseEntity.status(backendService.save(partner)).build();
        log.info("response entity : {}",responseEntity);
        return ResponseEntity.status(backendService.save(partner)).build();
        //return "redirect:/Confirmation_d_inscription.html";
    }

    @PostMapping("/submitMentor")
    public ResponseEntity submitMentor(@RequestBody Mentor mentor) {
        log.info("Processing request to save a new Mentor");

        return ResponseEntity.status(backendService.save(mentor)).build();
    }
}
