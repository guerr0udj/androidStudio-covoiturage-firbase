package com.example.covoiturageapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FirebaseMyFunctions {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    //String mail;
    //String password;

    Context context;


    public FirebaseMyFunctions( Context context) {
        //this.mail = mail;
        //this.password = password;
       this.context = context;
       this.auth=FirebaseAuth.getInstance();
       this.firebaseDatabase=FirebaseDatabase.getInstance("https://covoiturage-8e6b9-default-rtdb.europe-west1.firebasedatabase.app/");
    }







    public void registerUser(String mail, String pass) {

        auth.createUserWithEmailAndPassword(mail+"",pass+"").
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context, "user created successfully", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context, "error while wreating user", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }//registerUsers



    public interface UserExistsCallback {
        void onResult(boolean exists);
    }

    public void userExists(String mail, UserExistsCallback callback ){

        //verify existence of a user in firebaseAuth
        //this tunction is async so we hav to use countdowne or callback and await otherwise the return value will always be default
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
                    Toast.makeText(context, "error while verifying existence of mail in database", Toast.LENGTH_SHORT).show();
                }

            }//onComplete
        });//onCompleteListener

    }//userExists


}
