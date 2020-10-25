package ca.aretex.irex.experim.bean.request.data;

import ca.aretex.irex.experim.bean.request.Prospectable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Candidate implements Prospectable {

    @JsonUnwrapped
    Client client;
}
