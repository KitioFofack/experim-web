package ca.aretex.irex.experim.bean.request.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Client {
    String name;

    @JsonUnwrapped
    Contact contact;

    @JsonProperty("name") @JsonAlias("lead_name")
    public void setName(String name){
        this.name = name;
    }

    @JsonProperty("lead_name")
    public String getName(){
        return name;
    }
}
