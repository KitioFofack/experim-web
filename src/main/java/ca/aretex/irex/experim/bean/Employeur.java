package ca.aretex.irex.experim.bean;

import lombok.Data;

@Data
public class Employeur extends Client{
    private String companyName;
    private String leadName;

    @Override
    public String toString() {
        return String.format("{\"company_name\":\"%s\",\"lead_name\":\"%s\",\"email\":\"%s\",\"phone_number\":\"%s\"}",
                companyName, leadName, this.getEmail(), this.getPhone());
    }
}
