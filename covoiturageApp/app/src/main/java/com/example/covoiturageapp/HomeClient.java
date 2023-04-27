package com.example.covoiturageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

//import android.app.ActionBar;
import androidx.appcompat.app.ActionBar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class HomeClient extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView naview;
    ActionBarDrawerToggle toggle;
    ActionBar actionBar;
    FirebaseAuth auth;

    ProfileFragment profileFragment;

    Intent intent;
    String userType;
    Bundle bundle;

    TextView userNameHeader, userMailHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);
    //__________init_______________
        // navigation drawer elements and fragment display

        drawer=findViewById(R.id.drawer_client);
        naview=findViewById(R.id.naview_client);
        toolbar=findViewById(R.id.nav_toolbar);

        View headerView= naview.getHeaderView(0);
        userNameHeader=headerView.findViewById(R.id.nav_username);
        userMailHeader=headerView.findViewById(R.id.nav_usermail);


        //Toolbar init
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        naview.setNavigationItemSelectedListener(this);

        toggle= new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open_drawer,R.string.close_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();// this update the vue th add the actionBareDrawerToggle created in java



        //get intent and push it in the fragments in bundles
        intent= getIntent();
        userType= intent.getStringExtra("userType").toString();
        bundle=new Bundle();
       // default Fragment Displayed
        bundle.putString("userType",userType);
        profileFragment=new ProfileFragment();
        profileFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.nav_client_framelayout,profileFragment).commit();


        //firebase
        auth= FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String mailUserTxt= user.getEmail().toString();

        userMailHeader.setText(mailUserTxt);
        userNameHeader.setText(userType);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_profile_client:

                //bundle.putString("userType",userType);
                //profileFragment=new ProfileFragment();
                //profileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_client_framelayout, profileFragment ).commit();
                break;
            case R.id.menu_trajet_client:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_client_framelayout, new TrajetClientFragment()).commit();
                break;
            case R.id.menu_history_client:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_client_framelayout, new HistoriqueFragment()).commit();
                break;
            case R.id.menu_logout_client:
                auth.signOut();
                Toast.makeText(HomeClient.this, R.string.logged_out, Toast.LENGTH_SHORT).show();
                Intent intentLogout=new Intent(getBaseContext(),loginSubscribe.class);
                intentLogout.putExtra("userType",userType);
                startActivity(intentLogout);
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}