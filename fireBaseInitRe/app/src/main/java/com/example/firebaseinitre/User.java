package com.example.firebaseinitre;

import android.widget.EditText;
import android.widget.TextView;

public class User {
    String nom,prenom,number;

    public User(String nom, String prenom, String number) {
        this.nom = nom;
        this.prenom = prenom;
        this.number = number;

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


}
