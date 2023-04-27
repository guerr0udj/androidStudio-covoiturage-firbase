package com.example.covoiturageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class HomeDriver extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView naview;

    ActionBarDrawerToggle toggle;
    ActionBar actionBar;
    FirebaseAuth auth;

    Intent intent;

    Bundle bundle;

    String userType;

    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_driver);
        //___init___________________________
        toolbar=findViewById(R.id.nav_toolbar_driver);
        drawerLayout=findViewById(R.id.drawer_driver);
        naview=findViewById(R.id.naview_driver);


        //set up toolbar as action bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //setup the nav drawer
        naview.setNavigationItemSelectedListener(this);//to put -this- nav drawer must be implemented

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //fragment init

        //get intent and push it in the fragments in bundles
        intent= getIntent();
        userType= intent.getStringExtra("userType").toString();
        bundle=new Bundle();
        // default Fragment Displayed
        bundle.putString("userType",userType);
        profileFragment=new ProfileFragment();
        profileFragment.setArguments(bundle);

       getSupportFragmentManager().beginTransaction().add(R.id.nav_driver_framelayout, profileFragment).commit();


        //firebase___
        auth= FirebaseAuth.getInstance();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_profile_chauffeur:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_driver_framelayout, profileFragment).commit();
                break;
            case R.id.menu_trajet_chauffeur:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_driver_framelayout, new TrajetChauffeurFragment()).commit();
                break;
            case R.id.menu_cagnot_chauffeur:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_driver_framelayout, new CagnotFragment()).commit();
                break;
            case R.id.menu_logout_chauffeur:
                auth.signOut();
                Toast.makeText(HomeDriver.this, R.string.logged_out, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(),loginSubscribe.class));
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}