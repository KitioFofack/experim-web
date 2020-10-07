package ca.aretex.irex.experim.bean;

import lombok.Data;

@Data
public class Partner extends Client{
    private String company_name;
    private String lead_name;
    private String email_id;
    private String phone;

    @Override
    public String toString() {
        return String.format("{\"lead_name\":\"%s\",\"company_name\":\"%s\",\"email_id\":\"%s\",\"phone\":\"%s\"}", lead_name, company_name, email_id, phone);
    }
}
