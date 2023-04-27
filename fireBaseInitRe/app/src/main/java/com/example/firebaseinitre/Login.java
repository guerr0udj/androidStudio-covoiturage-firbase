package com.example.firebaseinitre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText email , password;

    TextView forgotPassword;

    FirebaseAuth auth;

    ImageView eyePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        /*----to do: get mail from the Suscribe then add  set mail edittext to gain time
         *---: add verification of email in the database if not indicate that it doesn't exist
         * ---:chek the mail format with Patterns.EMAIL_ADDRESS
         *
         */

        email=findViewById(R.id.mail_login);
        password=findViewById(R.id.password_login);
        btnLogin=findViewById(R.id.btn_login);
        forgotPassword=findViewById(R.id.forgot_password_login);
        eyePassword=findViewById(R.id.eyepassword_login);

        auth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass=password.getText().toString();
                String mail=email.getText().toString().trim();

                auth.signInWithEmailAndPassword(mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "you are logged_in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,home.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


               // Toast.makeText(Login.this, "you are logged_in", Toast.LENGTH_SHORT).show();
            }
        });

        eyePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "qsfqsdf", Toast.LENGTH_SHORT).show();

                int inputTypePass = password.getInputType();

                if (inputTypePass == InputType.TYPE_TEXT_VARIATION_PASSWORD) {

                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyePassword.setImageResource(R.drawable.baseline__eye_24);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    // inputTypePass=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                } else if (inputTypePass == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {

                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyePassword.setImageResource(R.drawable.baseline_gray_eye_24);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    //  inputTypePass=InputType.TYPE_TEXT_VARIATION_PASSWORD;
                }

            }

        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ResetPassword.class));

            }
        });




    }





}