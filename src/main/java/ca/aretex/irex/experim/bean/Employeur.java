package ca.aretex.irex.experim.bean;

import lombok.Data;

@Data
public class Employeur extends Client{
    private String nomEntreprise;
    private String nomDuContact;

    @Override
    public String toString() {
        return String.format("{\"company_name\":\"%s\",\"lead_name\":\"%s\",\"email_id\":\"%s\",\"phone\":\"%s\"}",
                this.getNomEntreprise(), this.getNomDuContact(), this.getEmail(), this.getPhone());
    }
}
