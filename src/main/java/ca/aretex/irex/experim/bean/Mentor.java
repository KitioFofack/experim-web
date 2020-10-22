package ca.aretex.irex.experim.bean;

import lombok.Data;

@Data
public class Mentor extends Client{
    private String nom;
    private String disponibilite;

    @Override
    public String toString() {
        return String.format("{\"lead_name\":\"%s\",\"email_id\":\"%s\",\"phone\":\"%s\"}", nom, this.getEmail(), this.getPhone());
    }
}