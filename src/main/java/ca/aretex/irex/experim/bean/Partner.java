package ca.aretex.irex.experim.bean;

import lombok.Data;

@Data
public class Partner extends Client{
    private String companyName;
    private String leadName;

    @Override
    public String toString() {
        return String.format("{\"lead_name\":\"%s\",\"company_name\":\"%s\",\"email_id\":\"%s\",\"phone\":\"%s\"}", leadName, companyName, this.getEmail(), this.getPhone());
    }
}
