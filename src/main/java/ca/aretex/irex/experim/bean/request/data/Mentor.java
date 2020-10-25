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
public class Mentor implements Prospectable {

    @JsonUnwrapped
    Client client;

    String disponibilite;

    @JsonProperty("availability")
    public String getDisponibilite(){
        return disponibilite;
    }
}