package com.example.covoiturageapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LoginFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String USER_TYPE="userType";

    // TODO: Rename and change types of parameters
    private String mParam1,  mParam2, userType;


    Button loginBtn , btnPopBack,btnPopSend;

    ImageView eye;

    EditText password, mail,mailPop;

    TextView forgotPassword;
    CheckBox resetConfirmation;

    FirebaseAuth auth;


    // firebaseDb;
    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String userType,String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        args.putString(USER_TYPE, userType);
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            userType= getArguments().getString(USER_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_login, container, false);
        //init____________________________
         ;
        eye=v.findViewById(R.id.eye_login_fragment);
        loginBtn=v.findViewById(R.id.btn_login_login_fragment);
        password=v.findViewById(R.id.password_login_fragment);
        mail=v.findViewById(R.id.mail_login_Fragment);
        forgotPassword=v.findViewById(R.id.forgot_password_login);

       int PASSWORD_INVISIBLE=password.getInputType();

        //popup reset password
        Dialog dialog= new Dialog(getContext());
        dialog.setContentView(R.layout.forgot_password_popup);
        Window windowDialog=dialog.getWindow();
        windowDialog.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        windowDialog.getAttributes().windowAnimations= com.google.android.material.R.style.MaterialAlertDialog_Material3_Animation;
        windowDialog.setLayout(v.getRootView().getLayoutParams().width,windowDialog.getAttributes().height);

        mailPop = dialog.findViewById(R.id.mail_forgot_password_);
        btnPopSend= dialog.findViewById(R.id.btn_forgot_password_popup);
        btnPopBack=dialog.findViewById(R.id.btn_back_popup);
        resetConfirmation=dialog.findViewById(R.id.reset_confirmation_popup);


        //firbase__________
        auth=FirebaseAuth.getInstance();

        //listners________________

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailTxt = mail.getText().toString().trim().toLowerCase();
                String passTxt = password.getText().toString().trim();

                boolean empty_blanks= TextUtils.isEmpty(mailTxt) || TextUtils.isEmpty(passTxt) ;


                if (empty_blanks) {
                    Toast.makeText(getContext(), R.string.empty_blanks, Toast.LENGTH_SHORT).show();
                } else {
                    //verify existence of account and signeIn
                    FirebaseMyFunctions ff = new FirebaseMyFunctions(getContext());
                    ff.userExists(mailTxt, new FirebaseMyFunctions.UserExistsCallback() {
                        @Override
                        public void onResult(boolean exists) {
                            if (!exists) {
                                Toast.makeText(getContext(), "this mail is not registred", Toast.LENGTH_SHORT).show();
                            } else {

                                //signIn
                                auth.signInWithEmailAndPassword(mailTxt, passTxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        FirebaseUser currentUser = auth.getCurrentUser();
                                        FirebaseMyFunctions ff= new FirebaseMyFunctions(getContext());

                                        ff.userExists(mailTxt, new FirebaseMyFunctions.UserExistsCallback() {
                                            @Override
                                            public void onResult(boolean exists) {
                                                if (!exists){
                                                    Toast.makeText(getContext(), R.string.user_do_not_exist, Toast.LENGTH_SHORT).show();
                                                }else{

                                                    switch (userType){
                                                        case "chauffeur":
                                                            Intent intentChauffeur =new Intent(getContext(), HomeDriver.class);
                                                            intentChauffeur.putExtra("userType",userType);
                                                            startActivity(intentChauffeur);
                                                            Toast.makeText(getContext(),  R.string.logged_in, Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case "client":
                                                            Intent intentClient =new Intent(getContext(), HomeClient.class);
                                                            intentClient.putExtra("userType",userType);
                                                            startActivity(intentClient);
                                                            Toast.makeText(getContext(),  R.string.logged_in, Toast.LENGTH_SHORT).show();
                                                            break;

                                                    }

                                                }

                                            }
                                        });


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "password incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                });//on success and on failure
                            }
                        }
                    });

                }

            }//on Click          
        });//login button On click


        //password see and hide
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputTypePass = password.getInputType();//129  ,128,144



                if (inputTypePass == PASSWORD_INVISIBLE /*129*/) {

                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eye.setImageResource(R.drawable.baseline_eye_green_24);


                } else if (inputTypePass == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD /*144*/) {

                    password.setInputType(PASSWORD_INVISIBLE);
                    eye.setImageResource(R.drawable.baseline_eye_gray_24);


                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


        btnPopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnPopSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty( mailPop.getText().toString().trim().toLowerCase())){
                auth.sendPasswordResetEmail(mailPop.getText().toString().trim().toLowerCase())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                            resetConfirmation.setVisibility(View.VISIBLE);
                    }
                });
                }else{
                    Toast.makeText(getContext(), R.string.empty_blanks, Toast.LENGTH_SHORT).show();
                }

            }
        });







        // Inflate the layout for this fragment
        return v;
    }//

    //____________functions and interfaces


}//mainClass