package ca.aretex.irex.experim.bean.request.data;

import ca.aretex.irex.experim.bean.request.Prospectable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employeur  implements Prospectable {
    private String nomEntreprise;

    @JsonUnwrapped
    Client client;

    @JsonProperty("company_name")
    public String getNomEntreprise(){
        return nomEntreprise;
    }

}
