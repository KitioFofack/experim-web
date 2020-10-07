package ca.aretex.irex.experim.bean;

import lombok.Data;

@Data
public class Partner extends Client{
    private String nom_entreprise;
    private String nom_contact;
    private String email;
    private String telephone;
}
