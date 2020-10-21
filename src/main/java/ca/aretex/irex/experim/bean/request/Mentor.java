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
public class Mentor extends Client {
    private String disponibilite;

    @JsonProperty("availability")
    public String getDisponibilite(){
        return disponibilite;
    }

//    @Override
//    public String toString() {
//        return String.format("{\"lead_name\":\"%s\",\"email_id\":\"%s\",\"phone\":\"%s\"}", this.getNom(), this.getEmail(), this.getPhone());
//    }
}
