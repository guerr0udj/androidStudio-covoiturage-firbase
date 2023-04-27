package com.example.covoiturageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class loginSubscribe extends AppCompatActivity {
    ConstraintLayout rootlayout;

    Toolbar toolbar;
    Button subscribe, login;

    String userType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_subscribe);
        //init_____________________________________________________________________________
        //Views
        rootlayout=findViewById(R.id.rootlayout_logsub);
        subscribe=rootlayout.findViewById(R.id.subscribe_button_logsub);
        login=findViewById(R.id.login_button_logsub);
        toolbar=findViewById(R.id.toolbar_login_subs);


        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnClickListener(view->{ onBackPressed();});
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //objects intent Arrays and stuff
        Intent intent= getIntent();
        userType= intent.getStringExtra("userType");
        Bundle bundle=new Bundle();
        //default Fragment Displayed
        if(!userType.isEmpty()){
        bundle.putString("userType",userType);
        LoginFragment loginFragment=new LoginFragment();
        loginFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.hostlayout_logsub,loginFragment).commit();
        }else{
            Toast.makeText(this, "error UserType null or empty", Toast.LENGTH_SHORT).show();
        }

        //Listeners_____________________________________________________________________

        login.setOnClickListener(view->{
            bundle.putString("userType",userType);
            LoginFragment loginFragment=new LoginFragment();
            loginFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.hostlayout_logsub,loginFragment).commit();


        });


        subscribe.setOnClickListener(view->{
            bundle.putString("userType",userType);
            SubscribFragment subscribFragment=new SubscribFragment();
            subscribFragment.setArguments(bundle);

            Toast.makeText(this, userType, Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.hostlayout_logsub,subscribFragment).commit();
        });



    }


    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}