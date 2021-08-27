package com.basecamp.wootlabbanking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextInputEditText email;
    private Button btnForgotPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.email);
        btnForgotPassword = findViewById(R.id.btn_reset_password);
        dialog = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String emailValue = email.getText().toString().trim();

        if(emailValue.isEmpty()){
            email.setError("Please provide an email");
            email.requestFocus();
            return;
        }

        dialog.setMessage("Sending reset email, please wait..");
        dialog.show();

        mAuth.sendPasswordResetEmail(emailValue).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(ForgetPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    email.setText("");
                    Toast.makeText(ForgetPasswordActivity.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}