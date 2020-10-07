package ca.aretex.irex.experim.bean;

import lombok.Data;

@Data
public class Candidate extends Client{
    private String nom;
    private String prenom;
    private String telephone;
    private String courriel;
}
