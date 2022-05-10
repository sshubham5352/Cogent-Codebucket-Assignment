package com.example.cogentcodebucketassignment.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.cogentcodebucketassignment.R;
import com.example.cogentcodebucketassignment.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //class-level variables
    private ActivityMainBinding binding;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}