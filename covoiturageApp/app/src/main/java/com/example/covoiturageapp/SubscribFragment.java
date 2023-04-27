package com.example.covoiturageapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubscribFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscribFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String USER_TYPE="userType";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String userType;
    TextView nom,prenom,password,numtel,mail;

    Button subsribeBtn;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    ImageView eye;

    CheckBox  checkBoxDigit, checkBoxLowercase ,checkBoxSpecilaChar, checkBoxUppercase, checkBoxMinLengthChar ,checkBoxWhiteSpace;
    public SubscribFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscribFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscribFragment newInstance(String param1, String param2,String userType) {
        SubscribFragment fragment = new SubscribFragment();
        Bundle args = new Bundle();
        args.putString(USER_TYPE,userType);
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
            userType=getArguments().getString(USER_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO: add Data in FirbaseDataBase chauffeur ou client selon le getArgument("userType") reçu

        //init________________________________________________________________________________________
        View viewFragment=inflater.inflate(R.layout.fragment_subscrib, container, false);

        nom= viewFragment.findViewById(R.id.nom_subscribe_fragment);
        eye= viewFragment.findViewById(R.id.eye_subscribe_fragment);
        mail=viewFragment.findViewById(R.id.mail_subscribe_fragment);
        numtel=viewFragment.findViewById(R.id.numtel_subscribe_fragment);
        prenom= viewFragment.findViewById(R.id.prenom_subscribe_fragment);
        password=viewFragment.findViewById(R.id.password_subs_fragment);
        subsribeBtn=viewFragment.findViewById(R.id.btn_subscribe_fragment);
        checkBoxDigit= viewFragment.findViewById(R.id.checkbox_onedigit_subscribe_fragment);
        checkBoxLowercase =viewFragment.findViewById(R.id.checkbox_lowercase_subscribe_fragment);
        checkBoxUppercase= viewFragment.findViewById(R.id.checkbox_uppercase_subscribe_fragment);
        checkBoxSpecilaChar=viewFragment.findViewById(R.id.checkbox_specialchar_subscribe_fragment);
        checkBoxWhiteSpace= viewFragment.findViewById(R.id.checkbox_whitespace_subscribe_fragment);
        checkBoxMinLengthChar= viewFragment.findViewById(R.id.checkbox_min8char_subscribe_fragment);

        LinearLayout checkboxLayout= viewFragment.findViewById(R.id.checkboxes_layout_subs_fragment);

        int PASSWORD_INVISIBLE= password.getInputType();
        //object arrays  intent and stuff


        //firebase_____________________
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance("https://covoiturage-8e6b9-default-rtdb.europe-west1.firebasedatabase.app/");

        //________________regex____________________
        // Email validation regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        // Password validation regular expression
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";


        //listeners____________________________________________________________________
        //verifying if user exist than sending data if not, verify regex mail password


        subsribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mailTxt = mail.getText().toString().trim();
                String passwordTxt = password.getText().toString().trim();
                String nomTxt = nom.getText().toString().trim();
                String prenomTxt = prenom.getText().toString();
                String numTelTxt = numtel.getText().toString();

                boolean emptyBlanks = TextUtils.isEmpty(passwordTxt) || TextUtils.isEmpty(mailTxt)
                        ||TextUtils.isEmpty( nomTxt) || TextUtils.isEmpty(prenomTxt) || TextUtils.isEmpty(numTelTxt);

                //check empty Blanks



                //regex check

                if (emptyBlanks) {
                    Toast.makeText(getContext(), R.string.empty_blanks, Toast.LENGTH_LONG).show();
                } else if (passwordTxt.length() < 8) {
                    Toast.makeText(getContext(), R.string.error_password_min_length, Toast.LENGTH_LONG).show();
                } else if (!mailTxt.matches(emailRegex)) {
                    Toast.makeText(getContext(), R.string.error_email_format, Toast.LENGTH_SHORT).show();

                } else if (!passwordTxt.matches(passwordRegex)) {
                    Toast.makeText(getContext(), R.string.error_password_incorrect_format, Toast.LENGTH_SHORT).show();

                } else{
                    //verify existence of user mail
                  FirebaseMyFunctions ff=new FirebaseMyFunctions(getContext());
                  ff.userExists(mailTxt, new FirebaseMyFunctions.UserExistsCallback() {
                      @Override
                      public void onResult(boolean exists) {

                          if (exists == true) {
                              Toast.makeText(getContext(),R.string.error_email_already_exists , Toast.LENGTH_SHORT).show();
                          } else {
                              //the code to execute and subscribe if the user dosen't exist

                              //ff.registerUser(mailTxt, passwordTxt);
                              auth.createUserWithEmailAndPassword(mailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                  @Override
                                  public void onComplete(@NonNull Task<AuthResult> task) {

                                      if(task.isComplete()){

                                          FirebaseUser currentUser=task.getResult().getUser() ;
                                          String userId=currentUser.getUid().toString();

                                          Profile profile=new Profile(nomTxt,prenomTxt,numTelTxt);

                                          firebaseDatabase.getReference("chauffeur").child(userId).child("profile").setValue(profile);
                                          firebaseDatabase.getReference("client").child(userId).child("profile").setValue(profile);



                                          requireActivity().getSupportFragmentManager().
                                                  beginTransaction().replace(R.id.hostlayout_logsub,new LoginFragment()).commit();

                                      }else{
                                          Toast.makeText(getContext(), R.string.error_account_creation, Toast.LENGTH_SHORT).show();
                                      }

                                  }//onCompleat
                              });//Creat User
                          }

                      }//onResult
                  });//userExistence


                }//if regex and connection
             }//onClick
        });//Subscribe Button setListner


    //checkbox regex
    password.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String pass = s.toString();

            String digitPattern = ".*\\d.*";
            String upperCasePattern = ".*[A-Z].*";
            String lowerCasePattern = ".*[a-z].*";
            String specialCharacterPattern = ".*[@#$%^&+=].*";
            String whitespacePattern = ".*\\s.*";

            // Check if password matches all regex patterns
            boolean haveDigitPattern=pass.matches(digitPattern);
            boolean haveUpperCasePattern=pass.matches(upperCasePattern);
            boolean haveLowerCasePatter=pass.matches(lowerCasePattern);
            boolean haveSpecialCharacterPattern=pass.matches(specialCharacterPattern);
            boolean haveWhiteSpacePattern=pass.matches(whitespacePattern);
            boolean haveMinLengthEight= (password.length() >= 8);

            boolean isPasswordValid = haveDigitPattern
                    && haveUpperCasePattern
                    && haveLowerCasePatter
                    && haveSpecialCharacterPattern
                    && !haveWhiteSpacePattern
                    && haveMinLengthEight;

            // Update UI based on password validity
            if (isPasswordValid) {
                // Password is valid
                password.setTextColor(Color.GREEN);
                checkboxLayout.setVisibility(View.GONE);
            } else {
                // Password is invalid
                password.setTextColor(Color.RED);
                checkboxLayout.setVisibility(View.VISIBLE);
            }

            checkBoxDigit.setChecked(haveDigitPattern);
            checkBoxLowercase.setChecked(haveLowerCasePatter);
            checkBoxSpecilaChar.setChecked(haveSpecialCharacterPattern);
            checkBoxUppercase.setChecked(haveUpperCasePattern);
            checkBoxMinLengthChar.setChecked(haveMinLengthEight);
            checkBoxWhiteSpace.setChecked(!haveWhiteSpacePattern);

        }
    });//password listner

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




        // Inflate the layout for this fragment
        return viewFragment;
    }//main SubscribeFragment Class



    //------------------------------------------------functions----------------------------------
    private void registerUser(String mail, String pass) {

        auth.createUserWithEmailAndPassword(mail+"",pass+"").
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), R.string.user_created, Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }//registerUsers
/*
    @Override
    public void onResult(boolean exists) {

    }



    public interface UserExistsCallback {
        void onResult(boolean exists);
    }

    private void userExists(String mail,UserExistsCallback callback ){

        //verify existence of a user in firebaseAuth
        //this tunction is async so we hav to use countdowne and await otherwise the return value will always be default
        auth.fetchSignInMethodsForEmail(mail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {

            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if(task.isSuccessful()){

                    boolean exists;
                    List<String> signInMethods=task.getResult().getSignInMethods();

                    if(task!=null && signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)){
                        //user already exists
                        exists=true;
                    }else{
                        //user does not exist
                        exists=false;
                    }
                    callback.onResult(exists);
                }else{
                    Toast.makeText(getContext(), "error while verifying existence of mail in database", Toast.LENGTH_SHORT).show();
                }

            }//onComplete
        });//onCompleteListener

    }//userExists

 */
}