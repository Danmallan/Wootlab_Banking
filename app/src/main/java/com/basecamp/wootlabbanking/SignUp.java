package com.basecamp.wootlabbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tvSignIn = findViewById(R.id.tv_sign_in);
        tvSignIn.setOnClickListener(v -> {
            startActivity(new Intent(SignUp.this, SignIn.class));
        });
    }
}