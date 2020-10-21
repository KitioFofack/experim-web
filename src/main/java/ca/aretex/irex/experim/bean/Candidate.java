package ca.aretex.irex.experim.bean;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Data
public class Candidate extends Client{
    private String nom;

    @Override
    public String toString() {
        return String.format("{\"lead_name\":\"%s\",\"email_id\":\"%s\",\"phone\":\"%s\"}", this.getNom(), this.getEmail(), this.getPhone());
    }

}
