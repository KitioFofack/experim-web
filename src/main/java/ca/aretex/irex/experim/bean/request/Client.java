package ca.aretex.irex.experim.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
    String name;
    String email;
    String phone;


    @JsonProperty("name")
    public void setName(String name){
        this.name = name;
    }
    @JsonProperty("email")
    public void setEmail(String email){
        this.email = email;
    }

    @JsonProperty("lead_name")
    public String getName(){
        return name;
    }
    @JsonProperty("email_id")
    public String getEmail(){
        return email;
    }
}
