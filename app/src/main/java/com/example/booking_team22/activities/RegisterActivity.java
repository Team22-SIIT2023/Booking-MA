package com.example.booking_team22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.booking_team22.databinding.ActivityRegisterBinding;
import com.example.booking_team22.databinding.ActivitySplashScreenBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}