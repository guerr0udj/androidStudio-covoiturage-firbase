package com.example.firebaseinitre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.number.Scale;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    //ui_element
    Button btnSubscribe, btnLogin, bottomBar;

    //db_elements
    FirebaseFirestore firestoreDb;
    FirebaseDatabase firebaseDb;
    DatabaseReference reference;
    FirebaseAuth auth;
    //btmbar
    EditText titleHome,titleEye;
    LinearLayout layoutHome,layoutEye;
    ImageView iconHome,iconEye;

    int HOME_ID=1,EYE_ID=2;
    int selectable=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

//--------------------------redirection------------------------
        btnLogin=findViewById(R.id.btn_login_main);
        btnSubscribe=findViewById(R.id.btn_subscribe_main);
//----------bottom bar elements_______
        layoutEye=findViewById(R.id.icon_layout_bottombar_main);
        layoutHome=findViewById(R.id.home_layout_bottombar_main);
        iconHome=findViewById(R.id.home_bottombar_main);
        iconEye=findViewById(R.id.icon_bottombar_main);
        //animation fo ui bar
        AnimationSet animSet=new AnimationSet(true);
        RotateAnimation rotAnim=new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ScaleAnimation scaleAnimation= new ScaleAnimation(0.5f,1f,0.5f,1f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.ZORDER_BOTTOM,0.5f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        rotAnim.setDuration(150);
        animSet.addAnimation(rotAnim);
        animSet.addAnimation(scaleAnimation);





        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Subscribe.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseContext(),Login.class));
            }
        });





//------------------------------Custom Bottom bar--------------------------------------------------

    layoutHome.setOnClickListener(view -> {
        if(selectable!=HOME_ID){
            //custom bottom bar Ui
            selectable=HOME_ID;
            iconHome.setImageResource(R.drawable.baseline_home_white_24);
            iconEye.setImageResource(R.drawable.baseline_gray_eye_24);
            layoutHome.setBackgroundResource(R.drawable.rounded_squar_solid);
            layoutEye.setBackground(null);



            layoutHome.startAnimation(scaleAnimation);
            iconHome.startAnimation(animSet);
            //add other things to do


        }

    });


    layoutEye.setOnClickListener(view ->{
        if(selectable!=EYE_ID){
            //custom bottom bar Ui
            selectable=EYE_ID;
            iconHome.setImageResource(R.drawable.baseline_home_24);
            iconEye.setImageResource(R.drawable.baseline__eye_24);
            layoutEye.setBackgroundResource(R.drawable.rounded_squar_solid);
            layoutHome.setBackground(null);


            layoutEye.startAnimation(scaleAnimation);
            iconEye.startAnimation(animSet);


        }
    });


//_______fake data test__________________________________
/*
        HashMap< String,Object > user = new HashMap<>();
        user.put("pseudo","kim");
        user.put("mail","kim@gmail.com");
//________________firestore basic______________________________

        firestoreDb= FirebaseFirestore.getInstance();
        firestoreDb.collection("userTab").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"send to firstore with success",Toast.LENGTH_LONG).show();
            }
        });

        Map<Object,Object> dataMap= new HashMap<>();
        dataMap.put("name","kikim");

 //___________________fireBase RealTimeDataBase basic_______________________________
        firebaseDb = FirebaseDatabase.getInstance("https://fir-initre-default-rtdb.europe-west1.firebasedatabase.app/");//"https://fir-initre-default-rtdb.europe-west1.firebasedatabase.app/"
        reference = firebaseDb.getReference("FirstTabTest");
        reference.setValue("subTabOne").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText( MainActivity.this, "sent to real time firbase ", Toast.LENGTH_LONG).show();

            }
        });
       reference.child("subTabTwo").setValue(dataMap);
*/

//----------------firbaseAuthentication basic_____________________________

    /*
    auth=FirebaseAuth.getInstance();
    auth.createUserWithEmailAndPassword("guru@gmail.com","2313213213");
*/



    }

    @Override
    public void onRecyclerItemClicked(int position) {

    }
}