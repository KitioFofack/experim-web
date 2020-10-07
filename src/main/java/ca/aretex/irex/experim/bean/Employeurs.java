package ca.aretex.irex.experim.bean;

import lombok.Data;

@Data
public class Employeurs {
    private String nom_entreprise;
    private String nom_du_contact;
    private String email;
    private String numero_de_telephone;


    public String getNom_entreprise() {
        return nom_entreprise;
    }

    public void setNom_entreprise(String nom_entreprise) {
        this.nom_entreprise = nom_entreprise;
    }

    public String getNom_du_contact() {
        return nom_du_contact;
    }

    public void setNom_du_contact(String nom_du_contact) {
        this.nom_du_contact = nom_du_contact;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero_de_telephone() {
        return numero_de_telephone;
    }

    public void setNumero_de_telephone(String numero_de_telephone) {
        this.numero_de_telephone = numero_de_telephone;
    }

}
