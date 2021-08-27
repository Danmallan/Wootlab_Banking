package com.basecamp.wootlabbanking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private TextView tvSignUp, tvSignIn, forgetPassword;
    private EditText email, password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private String emailValue, passwordValue;
    private ProgressDialog dialog;
    public static final String userEmail = "";

    public static final String TAG = "SignIn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        tvSignUp = findViewById(R.id.sign_up);
        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SignIn.this, SignUp.class));
        });

        forgetPassword = findViewById(R.id.forget_password);
        forgetPassword.setOnClickListener(v -> {
            startActivity(new Intent(SignIn.this, ForgetPasswordActivity.class));
        });

        tvSignIn = findViewById(R.id.sign_in);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mUser != null){
                    Intent intent = new Intent(SignIn.this, Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Log.e(TAG, "AuthStateChanged : Sign In ");

                }else {
                    Log.e(TAG, "AuthStateChanged : Logout");
                }
            }
        };

        tvSignIn.setOnClickListener(v -> {
            userSignIn();
        });

    }

    private void userSignIn() {
        emailValue = email.getText().toString().trim();
        passwordValue = password.getText().toString().trim();

        if(TextUtils.isEmpty(emailValue) || !emailValue.contains("@")){
            Toast.makeText(this, "Enter a valid email!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Enter a valid email!");
            return;
        }else if(TextUtils.isEmpty(emailValue)){
            Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Enter a valid password");
            return;
        }

        dialog.setMessage("Login user please wait ...");
        dialog.show();
        mAuth.signInWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(SignIn.this, "Login not Successful!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Login not Successful!");
                }else {
                    dialog.dismiss();
                    checkIfEmailIsVerified();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, e.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void checkIfEmailIsVerified() {
        FirebaseUser users = mAuth.getCurrentUser();
        boolean emailVerified = users.isEmailVerified();

        if(!emailVerified){
            Toast.makeText(this, "Please verify your email Id", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        } else {
            email.getText().clear();
            password.getText().clear();

            Intent intent = new Intent(SignIn.this, Home.class);
            intent.putExtra(userEmail, emailValue);
            startActivity(intent);

        }
    }
}