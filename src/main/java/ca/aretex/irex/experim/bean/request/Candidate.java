package ca.aretex.irex.experim.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Candidate extends Client {
//    @Override
//    public String toString() {
//        return String.format("{\"lead_name\":\"%s\",\"email_id\":\"%s\",\"phone\":\"%s\"}", this.getNom(), this.getClientContact().getEmail(), this.getClientContact().getPhone());
//    }
}
