package com.basecamp.wootlabbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.e("Home", "OnCreate ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Home", "onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Home", "onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("Home", "onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Home", "onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}