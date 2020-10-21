package ca.aretex.irex.experim.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Partner extends Employeur {
//    @Override
//    public String toString() {
//        return String.format("{\"lead_name\":\"%s\",\"company_name\":\"%s\",\"email_id\":\"%s\",\"phone\":\"%s\"}", leadName, companyName, this.getEmail(), this.getPhone());
//    }
}
