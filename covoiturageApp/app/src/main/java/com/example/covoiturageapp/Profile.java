package com.example.covoiturageapp;

public class Profile {
    String nom, prenom, numeroTel;

    public Profile() {

    }

    public Profile(String nom, String prenom, String numeroTel) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTel = numeroTel;
    }



    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumeroTel() {
        return numeroTel;
    }
}
