package com.example.booking_team22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.booking_team22.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    SharedPreferences sp;
    String email,userType;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupButton.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        sp=getSharedPreferences("mySharedPrefs",MODE_PRIVATE);

        binding.loginButton.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            email=binding.usernameField.getText().toString();
            userType=binding.usernameField.getText().toString();
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("email",email);
            editor.putString("userType",userType);
            editor.commit();

            startActivity(intent);
        });
    }
}
