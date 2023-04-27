package com.example.firebaseinitre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class Subscribe extends AppCompatActivity {

    Button btnSubscribe;
    EditText password,email;

    FirebaseAuth auth;


    CheckBox checkBoxSpecilaChar,checkBoxWhiteSpace,checkBoxMinLengthChar
            ,checkBoxUppercase,checkBoxLowercase,checkBoxDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        //___________ui elements xml__________________________
        btnSubscribe=findViewById(R.id.btn_register_subscribe);
        password=findViewById(R.id.password_subscribe);
        email=findViewById(R.id.email_subscribe);
        checkBoxUppercase=findViewById(R.id.checkbox_uppercase_subscribe);
        checkBoxDigit=findViewById(R.id.checkbox_onedigit_subscribe);
        checkBoxLowercase=findViewById(R.id.checkbox_lowercase_subscribe);
        checkBoxMinLengthChar=findViewById(R.id.checkbox_min8char_subscribe);
        checkBoxSpecilaChar=findViewById(R.id.checkbox_specialchar_subscribe);
        checkBoxWhiteSpace=findViewById(R.id.checkbox_whitespace_subscribe);
        //_________db auth init-----------------------
        auth= FirebaseAuth.getInstance()
        ;
        //________________regex____________________
        // Email validation regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        // Password validation regular expression
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";





        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass= password.getText().toString();
                String mail= email.getText().toString();

                if( TextUtils.isEmpty(pass) || TextUtils.isEmpty(mail) ){
                    Toast.makeText(Subscribe.this, "mail or password is empty", Toast.LENGTH_LONG).show();
                } else if (pass.length()<8) {
                    Toast.makeText(Subscribe.this, "password is less then 8 characters", Toast.LENGTH_LONG).show();
                } else if (!mail.matches(emailRegex) ) {
                    Toast.makeText(Subscribe.this, "email incorrect format", Toast.LENGTH_SHORT).show();
                    
                }else if (!pass.matches(passwordRegex)){
                    Toast.makeText(Subscribe.this, "password incorrect format", Toast.LENGTH_SHORT).show();

                } else {

                    userExists(mail, new UserExistsCallback() {
                        @Override
                        public void onResult(boolean exists) {
                            if(exists==true){
                                Toast.makeText(Subscribe.this, "this email is already used", Toast.LENGTH_SHORT).show();
                            }else {
                                registerUser(mail,pass);
                            }


                        }
                    });



                    //

                }
            }

        });//btnSubscribe onClickListener



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
                        && !haveWhiteSpacePattern;

                // Update UI based on password validity
                if (isPasswordValid) {
                    // Password is valid
                    password.setTextColor(Color.GREEN);
                } else {
                    // Password is invalid
                    password.setTextColor(Color.RED);
                }

                checkBoxDigit.setChecked(haveDigitPattern);
                checkBoxLowercase.setChecked(haveLowerCasePatter);
                checkBoxSpecilaChar.setChecked(haveSpecialCharacterPattern);
                checkBoxUppercase.setChecked(haveUpperCasePattern);
                checkBoxMinLengthChar.setChecked(haveMinLengthEight);
                checkBoxWhiteSpace.setChecked(!haveWhiteSpacePattern);

            }
        });


    }//onCreate mainClass





//------------------------------------------------functions----------------------------------
    private void registerUser(String mail, String pass) {

        auth.createUserWithEmailAndPassword(mail+"",pass+"").
                addOnCompleteListener(Subscribe.this,new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(Subscribe.this,Login.class));

                }else {
                    Toast.makeText(Subscribe.this, "error while sending data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }//registerUsers



    private interface UserExistsCallback {
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
                    Toast.makeText(Subscribe.this, "error while verifying existence of mail in database", Toast.LENGTH_SHORT).show();
                }

            }//onComplete
        });//onCompleteListener

    }//userExists



}//mainClass