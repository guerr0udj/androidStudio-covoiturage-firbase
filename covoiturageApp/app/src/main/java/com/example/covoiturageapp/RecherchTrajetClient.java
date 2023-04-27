package com.example.covoiturageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class RecherchTrajetClient extends AppCompatActivity implements  RecyclerViewInterface{
    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherch_trajet_client);

        recyclerView= findViewById(R.id.recycler_recherch_trajet_client);

        firebaseDatabase= FirebaseDatabase.getInstance("https://covoiturage-8e6b9-default-rtdb.europe-west1.firebasedatabase.app/");




        Intent intent=getIntent();
        String villeDepart= intent.getStringExtra("villeDepart");
        String villeArrivee= intent.getStringExtra("villeArrivee");
        String dateTrajet= intent.getStringExtra("dateTrajet");

       // Toast.makeText(this, villeArrivee+" -lkdfj- "+villeDepart+" -;k,skndf- "+dateTrajet, Toast.LENGTH_SHORT).show();

        Trajet trajet = new Trajet("12:15","tours","paris","24/03/2023","lolo");
        Trajet trajet2 = new Trajet("07:25","marseille","lyon","28/03/2023","toto");

        ArrayList<Trajet> trajetInfoList = new ArrayList<Trajet>();
        trajetInfoList.add(trajet);
        trajetInfoList.add(trajet2);



        //Toast.makeText(this, ""+trajetInfoList.size()+" "+trajetInfoList, Toast.LENGTH_SHORT).show();

        RecherchTrajetClientAdapter recyclerViewAdapter= new RecherchTrajetClientAdapter(this,trajetInfoList,this);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DatabaseReference ref=firebaseDatabase.getReference("chauffeur");


        // partie a retravailller pour get la list des traje et les reafficher correctement avec un filtre
        // retravailler la structure firebase et call les donnée filtrer avec des query firbase

        /*
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot trajet: snapshot.getChildren()){

                    Trajet trajetInfoFromSnapshot = trajet.child("trajet").getValue(Trajet.class);

                    String villDeprat= trajet.child("trajetInfo").child("pointDepart").getValue(String.class).toLowerCase().trim();
                    String villArivee= trajet.child("trajetInfo").child("pointArrivee").getValue(String.class).toLowerCase().trim();
                    String arr=villArivee;
                    String dep=villDeprat;



                    trajetInfoList.add(trajetInfoFromSnapshot);

                    Collections.sort(trajetInfoList, new Comparator<Trajet>() {
                        @Override
                        public int compare(Trajet t1, Trajet t2) {
                            String time1 =  t1.getHeursDepart().replaceAll("\\D","").trim();//data stored "hh:mm". i need "hhmm"
                            String time2 = t2.getHeursDepart().replaceAll("\\D","").trim();

                            Toast.makeText(RecherchTrajetClient.this, time1+" "+time2, Toast.LENGTH_SHORT).show();

                            return time1.compareTo(time2);
                        }
                    });

                }

                recyclerViewAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.getMessage();
            }
        });
*/











    }

    @Override
    public void onRecyclerItemClicked(int position) {

    }
}