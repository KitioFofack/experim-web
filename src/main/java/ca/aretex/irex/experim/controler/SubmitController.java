package ca.aretex.irex.experim.controler;

import ca.aretex.irex.experim.bean.Candidate;
import ca.aretex.irex.experim.bean.Mentor;
import ca.aretex.irex.experim.bean.Employeur;
import ca.aretex.irex.experim.bean.Partner;
import ca.aretex.irex.experim.service.ERPNextRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SubmitController {

    private static Logger logger= LoggerFactory.getLogger(SubmitController.class);

    @Autowired
    private ERPNextRepository backendService;
   @PostMapping("/submitCandidate")
    public ResponseEntity submitCandidate(@RequestBody Candidate candidate){
        return ResponseEntity.status(backendService.save(candidate)).build();
    }

    @PostMapping("/submitEmployeurs")
    public ResponseEntity submitEmployeurs(@RequestBody Employeur employeur){
        return ResponseEntity.status(backendService.save(employeur)).build();
    }

    @PostMapping("/submitPartner")
    public ResponseEntity submitPartner(@RequestBody Partner partner) {
        return ResponseEntity.status(backendService.save(partner)).build();
    }

    @PostMapping("/submitMentor")
    public ResponseEntity submitMentor(@RequestBody Mentor mentor)
    {
        return ResponseEntity.status(backendService.save(mentor)).build();
    }
}
