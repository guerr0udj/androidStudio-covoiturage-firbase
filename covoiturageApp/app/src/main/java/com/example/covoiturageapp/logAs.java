package com.example.covoiturageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class logAs extends AppCompatActivity {

    Button chauffeur, client;
    FrameLayout rootlayout;
    String userType;
    ImageView eye;
    EditText mail,password;
    Button loginBtn;
    TextView forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_as);

        rootlayout=findViewById(R.id.rootlayout_logas);
        chauffeur=rootlayout.findViewById(R.id.bouttonChauffeur_logas);
        client=rootlayout.findViewById(R.id.bouttonClient_logas);

        Intent intent= new Intent(logAs.this, loginSubscribe.class);

        chauffeur.setOnClickListener( view -> {
            userType="chauffeur";
            intent.putExtra("userType",userType);
            startActivity(intent);
        });


        client.setOnClickListener(view->{
            userType="client";
            intent.putExtra("userType",userType);
            startActivity(intent);
        });











    }
}