package ca.aretex.irex.experim.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employeur extends Client {
    private String nomEntreprise;

    @JsonProperty("company_name")
    public String getNomEntreprise(){
        return nomEntreprise;
    }

//    @Override
//    public String toString() {
//        return String.format("{\"company_name\":\"%s\",\"lead_name\":\"%s\",\"email_id\":\"%s\",\"phone\":\"%s\"}",
//                getNomEntreprise(), getNomDuContact(), getClientContact().getEmail(), getClientContact().getPhone());
//    }
}
