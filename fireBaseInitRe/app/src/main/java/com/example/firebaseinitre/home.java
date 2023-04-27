package com.example.firebaseinitre;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class home extends AppCompatActivity  {
    Button btnLogout,btnSaveData,btnUsersList,btnTest;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    EditText nom,prenom,number,pointDepart,pointArrivee;

    TextView heureDepart,heureArrivee;

    LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //buttons
        btnUsersList=findViewById(R.id.btn_users_list_profile_home);
        btnLogout=findViewById(R.id.btn_logout);
        btnSaveData=findViewById(R.id.save_data_profile_home);
        btnTest=findViewById(R.id.btn_test_profile_home);
        //views
         heureDepart = findViewById(R.id.heure_depart_profile_home);
               heureArrivee=findViewById(R.id.heure_arrivee_profile_home);
        rootView=findViewById(R.id.home_root_layout);
        nom=rootView.findViewById(R.id.nom_profile_home);
        prenom=rootView.findViewById(R.id.prenom_profile_home);
        number=rootView.findViewById(R.id.numero_profile_home);
        pointDepart =findViewById(R.id.point_depart_profile_home);
        pointArrivee = findViewById(R.id.point_arrivee_profile_home);



        //database
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance("https://fir-initre-default-rtdb.europe-west1.firebasedatabase.app");
        FirebaseUser user=auth.getCurrentUser();
        //verification of connection

        /*
            if (user != null) {
                Toast.makeText(this, "welcome " + user.getUid().toString(), Toast.LENGTH_SHORT).show();
                // add more data to the user's node as needed
            } else {
                // User is not signed in
                Toast.makeText(getBaseContext(), "cant find user, verify connection", Toast.LENGTH_SHORT).show();
            }
        */

        btnUsersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this,UsersList.class));
            }
        });




    btnLogout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            auth.signOut();
            Toast.makeText(home.this, "signed_out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getBaseContext(),Login.class));
            finish();
        }
    });//logOutListener


        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //_________get user id and mail______
                String userId ;
                String userMail;
                if (user != null) {

                    userId = user.getUid();
                    userMail= user.getEmail();

                    DatabaseReference userRef = firebaseDatabase.getReference("users").child(userId);
                    TrajetInfo trajetInfo= new TrajetInfo(nom.getText().toString(),prenom.getText().toString(),
                            number.getText().toString(), pointDepart.getText().toString(),
                            pointArrivee.getText().toString(),heureDepart.getText().toString(),heureArrivee.getText().toString());
                    userRef.child("trajetInfo").setValue(trajetInfo);
                    /*
                    userRef.child("email").setValue(userMail);
                    userRef.child("nom").setValue(nom.getText().toString());
                    userRef.child("prenom").setValue(prenom.getText().toString());
                    userRef.child("numeroTel").setValue(number.getText().toString());
                    userRef.child("heureDepart").setValue(heureDepart.getText().toString());
                    userRef.child("heureArrivee").setValue(heureArrivee.getText().toString());
                    userRef.child("pointDepart").setValue(pointDepart.getText().toString());
                    userRef.child("PointDarrivee").setValue(pointArrivee.getText().toString());

                     */

                    // add more data to the user's node as needed
                } else {
                    // User is not signed in
                    Toast.makeText(getBaseContext(), "cant find user, verify connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarNow= Calendar.getInstance();
                int hour= calendarNow.get(Calendar.HOUR_OF_DAY);
                int minutes=calendarNow.get(Calendar.MINUTE);
                /*
                TimePickerDialog.OnTimeSetListener timePickerListner= new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                };

                TimePickerDialog timePickerDialog= new TimePickerDialog(home.this,2,timePickerListner,hour,minutes,false);
                */
                TimePickerDialog timePickerDialog= new TimePickerDialog(home.this,3, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Toast.makeText(home.this, "timeDialog listner", Toast.LENGTH_SHORT).show();
                        if (heureDepart instanceof EditText) {
                            ((EditText) heureDepart).setText (String.format("%02d:%02d", hourOfDay, minute));
                        } else if (heureArrivee instanceof EditText) {
                            ((TextView) heureArrivee).setText (String.format("%02d:%02d", hourOfDay, minute));
                        }
                    }
                } ,hour,minutes,true);
                timePickerDialog.show();

            }
        });


        heureDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //timePickerShowUp(heureDepart);
                Toast.makeText(home.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        heureArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerShowUp(heureArrivee);

            }
        });


    }//onCreate Home





    private  void timePickerShowUp(View viewInput){


        Calendar calendarNow= Calendar.getInstance();
        int hour= calendarNow.get(Calendar.HOUR_OF_DAY);
        int minutes=calendarNow.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timePickerListner= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (viewInput instanceof EditText) {
                    ((EditText) viewInput).setText (String.format("%02d:%02d", hourOfDay, minute));
                } else if (viewInput instanceof TextView) {
                    ((TextView) viewInput).setText (String.format("%02d:%02d", hourOfDay, minute));
                }else if (viewInput instanceof Button) {
                    ((Button) viewInput).setText (String.format("%02d:%02d", hourOfDay, minute));
                }
            }
        };

        TimePickerDialog timePickerDialog= new TimePickerDialog(
                home.this,2,timePickerListner,hour,minutes,true);

        timePickerDialog.show();

    }//timePickerShowUp function


}//MainClass Home