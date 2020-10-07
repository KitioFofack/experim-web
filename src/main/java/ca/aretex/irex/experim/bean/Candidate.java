package ca.aretex.irex.experim.bean;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Data
public class Candidate {
    private String nom;
    private String prenom;
    private String telephone;
    private String courriel;

}
