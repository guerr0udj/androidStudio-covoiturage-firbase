package com.example.firebaseinitre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UsersList extends AppCompatActivity implements RecyclerViewInterface{
FirebaseAuth auth;
FirebaseDatabase firebaseDatabase;
RecyclerView recyclerView;

FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        //database Firebase

        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance("https://fir-initre-default-rtdb.europe-west1.firebasedatabase.app/");
        firebaseUser=auth.getCurrentUser();
        String userId= firebaseUser.getUid();
        //RecyclerView
        recyclerView=findViewById(R.id.recyclerview_users_list);

        //list and object to collect data
        TrajetInfo trajetInfo1=new TrajetInfo("gugu","kikim","07215431","7:05","8:09","paris","Tours");
        TrajetInfo trajetInfo2=new TrajetInfo("guu","ki","0755136984","10:05","18:00","paris","Tours");

        ArrayList<TrajetInfo> trajetInfoList = new ArrayList<TrajetInfo>();

        trajetInfoList.add(trajetInfo1);
        trajetInfoList.add(trajetInfo2);

        //Toast.makeText(this, ""+trajetInfoList.size()+" "+trajetInfoList, Toast.LENGTH_SHORT).show();

        RecyclerViewAdapter recyclerViewAdapter= new RecyclerViewAdapter(this,trajetInfoList,this);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





       // Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();users trajetInfo

        DatabaseReference ref=firebaseDatabase.getReference("users");//.child(userId).child("trajetInfo");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot trajet: snapshot.getChildren()){
                   TrajetInfo trajetInfoFromSnapshot = trajet.child("trajetInfo").getValue(TrajetInfo.class);

                    String villDeprat= trajet.child("trajetInfo").child("pointDepart").getValue(String.class).toLowerCase().trim();
                    String villArivee= trajet.child("trajetInfo").child("pointArrivee").getValue(String.class).toLowerCase().trim();
                    String arr="marseille";
                    String dep= "paris";
                   // Toast.makeText(UsersList.this, villArivee+" "+villDeprat + " snap ", Toast.LENGTH_SHORT).show();
                    //filter data after snapshot.children with java _
                    // we can also do query (to try implement ans see diff)
                    /*
                    if( (villDeprat.equals(dep) && villArivee.equals(arr)) ){
                        trajetInfoList.add(trajetInfoFromSnapshot);
                    }else {
                        Toast.makeText(UsersList.this, "pas de trajet "+ villDeprat+" "+villArivee, Toast.LENGTH_SHORT).show();
                    }*/


                    trajetInfoList.add(trajetInfoFromSnapshot);

                    Collections.sort(trajetInfoList, new Comparator<TrajetInfo>() {
                        @Override
                        public int compare(TrajetInfo t1, TrajetInfo t2) {
                            String time1 =  t1.getHeureDepart().replaceAll("\\D","").trim();//data stored "hh:mm". i need "hhmm"
                            String time2 = t2.getHeureDepart().replaceAll("\\D","").trim();

                            Toast.makeText(UsersList.this, time1+" "+time2, Toast.LENGTH_SHORT).show();

                            return time1.compareTo(time2);
                        }
                    });

                }

                recyclerViewAdapter.notifyDataSetChanged();

                Log.d("##########________########:::::",  "yo");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    error.getMessage();
            }
        });

      //  Toast.makeText(getBaseContext(), trajetInfoList.size()+"  "+ trajetInfoList , Toast.LENGTH_LONG).show();

        Log.d("__________------########-------________", trajetInfoList.size()+"  "+ trajetInfoList  );


    }


    @Override
    public void onRecyclerItemClicked(int position) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
    }
}