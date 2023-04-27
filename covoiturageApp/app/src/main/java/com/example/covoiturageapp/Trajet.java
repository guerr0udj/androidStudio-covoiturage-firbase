package com.example.covoiturageapp;

public class Trajet {

    String heursDepart, heursArrivee, villeDepart,villeArrivee,dateTrajet,nom;

    public Trajet(){}

    public Trajet(String heursDepart, String villeDepart, String villeArrivee, String dateTrajet, String nom) {
        this.heursDepart = heursDepart;
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
        this.dateTrajet = dateTrajet;
        this.nom=nom;

    }


    public String getHeursDepart() {
        return heursDepart;
    }

    public String getHeursArrivee() {
        return heursArrivee;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public String getDateTrajet() {
        return dateTrajet;
    }

    public String getNom() {
        return nom;
    }

}
