package com.basecamp.wootlabbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private TextView tvSignUp, tvSignIn;
    private EditText email, password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private String emailValue, passwordValue;
    private ProgressDialog dialog;
    public static final String userEmail = "";

    public static final String TAG = "LOGIN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        tvSignUp = findViewById(R.id.sign_up);
        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SignIn.this, SignUp.class));
        });

        tvSignIn = findViewById(R.id.sign_in);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        dialog = new ProgressDialog(this);

    }
}