package com.example.booking_team22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.booking_team22.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupButton.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        binding.loginButton.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}