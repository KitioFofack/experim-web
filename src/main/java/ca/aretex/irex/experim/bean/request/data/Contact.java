package ca.aretex.irex.experim.bean.request.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact {
    String email;
    String phone;

    @JsonProperty("email") @JsonAlias("email_id")
    public void setEmail(String email){
        this.email = email;
    }

    @JsonProperty("email_id")
    public String getEmail(){
        return email;
    }
}
