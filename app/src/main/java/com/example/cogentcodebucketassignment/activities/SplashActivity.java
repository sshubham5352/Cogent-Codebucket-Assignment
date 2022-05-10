package com.example.cogentcodebucketassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cogentcodebucketassignment.R;
import com.example.cogentcodebucketassignment.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SessionManager.createSessionManager(getApplicationContext());

//        ALTERING FLOW OF CODE
//        startActivity(new Intent(this, LanguagePreferenceActivity.class));
//        finish();


        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (SessionManager.isLoggedIn()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
            }

            finish();
        }, 1000);
    }
}