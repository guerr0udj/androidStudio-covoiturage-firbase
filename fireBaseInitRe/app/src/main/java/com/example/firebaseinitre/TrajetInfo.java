package com.example.firebaseinitre;

public class TrajetInfo {
    String nom,prenom,number,pointDepart,pointArrivee,heureDepart,heureArrivee;

    public TrajetInfo(){

    }

    public TrajetInfo(String nom, String prenom, String number, String pointDepart, String pointArrivee, String heureDepart, String heureArrivee) {
        this.nom = nom;
        this.prenom = prenom;
        this.number = number;
        this.pointDepart = pointDepart;
        this.pointArrivee = pointArrivee;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        //ajouter prix , nbrUtilisateur,
        //ajouter Class pour les ID clients
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumber() {
        return number;
    }

    public String getPointDepart() {
        return pointDepart;
    }

    public String getPointArrivee() {
        return pointArrivee;
    }

    public String getHeureDepart() {

        return heureDepart;
    }

    public String getHeureArrivee() {
        return heureArrivee;
    }
}
