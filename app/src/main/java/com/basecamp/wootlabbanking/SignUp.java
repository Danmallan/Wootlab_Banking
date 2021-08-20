package com.basecamp.wootlabbanking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.basecamp.wootlabbanking.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSignIn, tvSignUp;
    private EditText firstName, lastName, email, password;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String firstNameValue, lastnameValue, emailValue, passwordValue;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tvSignIn = findViewById(R.id.tv_sign_in);
        tvSignIn.setOnClickListener(v -> {
            startActivity(new Intent(SignUp.this, SignIn.class));
        });

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        tvSignUp = findViewById(R.id.sign_up);

//        for authentication using firebase
        mAuth = FirebaseAuth.getInstance();
        tvSignUp.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


    }

    @Override
    public void onClick(View v) {
        if(v == tvSignUp){
            userRegistration();
        }else if(v == tvSignIn){
            startActivity(new Intent(SignUp.this, SignIn.class));
        }
    }

    //    Get the value of text from users and validate the fields
//    Register users
//    Shortcut to arrage your code on android studio CTRL + ALT + L
    private void userRegistration() {
        firstNameValue = firstName.getText().toString().trim();
        lastnameValue = lastName.getText().toString().trim();
        emailValue = email.getText().toString().trim();
        passwordValue = password.getText().toString().trim();

        if(TextUtils.isEmpty(firstNameValue)){
            Toast.makeText(this, "Please enter firstname", Toast.LENGTH_SHORT).show();
            return;
        } else if(TextUtils.isEmpty(lastnameValue)){
            Toast.makeText(this, "Please enter lastname", Toast.LENGTH_SHORT).show();
            return;
        } else if(TextUtils.isEmpty(emailValue)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(passwordValue) || passwordValue.length() < 8 ){
            Toast.makeText(this, "Invalid password or password < 8", Toast.LENGTH_SHORT).show();
            return;
        }

//        Display dialog message
        mDialog.setMessage("Creating User please wait...");
        mDialog.setCancelable(false);
        mDialog.show();


//        Creating the user on
        mAuth.createUserWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                sendEmailVerification();
                mDialog.dismiss();
                onAuth(task.getResult().getUser());
                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        }).addOnFailureListener(e -> {
            Log.e("Sign Up ", e.getMessage());
        });

    }

    private void onAuth(FirebaseUser user) {
        createANewUser(user.getUid());
    }

    private void createANewUser(String uid) {
        User user = buildNewUser();
        mDatabase.child(uid).setValue(user);
    }

    private User buildNewUser() {
        return new User(
                firstNameValue,
                lastnameValue,
                emailValue,
                new Date().getTime()
        );
    }

    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
//                    Shortcut to generate Toast Toast + tab
                    Toast.makeText(this, "Check your email for verification", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }
            });
        }
    }

}