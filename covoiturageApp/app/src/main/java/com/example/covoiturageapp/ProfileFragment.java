package com.example.covoiturageapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String USER_TYPE = "userType";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String userType;


    TextView nom,prenom,phoneNumber;

    Button btnSaveModify,btnCancel;

    FirebaseAuth auth;

    FirebaseDatabase   firebaseDatabase;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2, String userType) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(USER_TYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            userType=getArguments().getString(USER_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_profile, container, false);

        //__init_______________________
        btnCancel =v.findViewById(R.id.btn_cancel_profile_fragment);
        btnSaveModify=v.findViewById(R.id.btn_modify_save_profile_fragment);
        nom=v.findViewById(R.id.nom_profile_fragment);
        prenom=v.findViewById(R.id.prenom_profile_fragment);
        phoneNumber=v.findViewById(R.id.phone_profile_fragment);

        //firebase________________________
        auth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance("https://covoiturage-8e6b9-default-rtdb.europe-west1.firebasedatabase.app/");
        String uid= auth.getUid();

        //init_data_profile_fragment

        Toast.makeText(getContext(), userType, Toast.LENGTH_SHORT).show();



        firebaseDatabase.getReference(userType).child(uid).child("profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Profile profile = snapshot.getValue(Profile.class);
                nom.setText(profile.getNom());
                prenom.setText(profile.getPrenom());
                phoneNumber.setText(profile.getNumeroTel());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        btnSaveModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();

                if(btnCancel.getVisibility()==View.GONE){
                    btnCancel.setVisibility(View.VISIBLE);
                    btnSaveModify.setText(R.string.save_button);

                    nom.setEnabled(true);
                    prenom.setEnabled(true);
                    phoneNumber.setEnabled(true);

                }else if(btnCancel.getVisibility()==View.VISIBLE){
                    //save data in to database


                    Profile profile=new Profile(nom.getText().toString().trim(),
                            prenom.getText().toString().trim(),
                            phoneNumber.getText().toString().trim());


                    firebaseDatabase.getReference("chauffeur").child(uid).child("profile").setValue(profile);
                    firebaseDatabase.getReference("client").child(uid).child("profile").setValue(profile);
                    // same same for nwo but i can have a different profile for userType different so i have the var usertype

                    btnSaveModify.setText(R.string.modify_button);
                    btnCancel.setVisibility(View.GONE);

                    nom.setEnabled(false);
                    prenom.setEnabled(false);
                    phoneNumber.setEnabled( false);

                    Toast.makeText(getContext(), "profile modifier", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnSaveModify.setText(R.string.modify_button);
                btnCancel.setVisibility(View.GONE);
                nom.setEnabled(false);
                prenom.setEnabled(false);
                phoneNumber.setEnabled( false);
            }
        });


        return v;}
}