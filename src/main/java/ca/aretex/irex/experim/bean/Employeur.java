package ca.aretex.irex.experim.bean;

import lombok.Data;

@Data
public class Employeur extends Client{
    private String nomEntreprise;
    private String nomDuContact;

    @Override
    public String toString() {
        return String.format("{\"enterprise_name\":\"%s\",\"conctact_name\":\"%s\",\"email\":\"%s\",\"phone_number\":\"%s\"}",
                this.getNomEntreprise(), this.getNomDuContact(), this.getPhone(), this.getEmail());
    }
}
