package com.example.booking_team22.activities;

import static com.example.booking_team22.clients.ClientUtils.authenticationService;
import static com.example.booking_team22.clients.ClientUtils.userService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.booking_team22.databinding.ActivityLoginBinding;
import com.example.booking_team22.fragments.users.admin.AccommodationApprovalFragment;
import com.example.booking_team22.model.User;
import com.example.booking_team22.model.UserCredentials;
import com.example.booking_team22.model.UserTokenState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    SharedPreferences sp;
    String email,userType;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        sp = getSharedPreferences("mySharedPrefs", MODE_PRIVATE);

        binding.loginButton.setOnClickListener(v -> {
            String username = binding.usernameField.getText().toString();
            String password = binding.passwordField.getText().toString();

            boolean isAllFieldChecked = checkAllFields(username, password);

            if (isAllFieldChecked) {
                UserCredentials userCredentials = new UserCredentials(username, password);
                authenticationService.authenticateUser(userCredentials).observe(this, userTokenState -> {
                    if (userTokenState != null) {
                        binding.usernameField.setText("");
                        binding.passwordField.setText("");
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("accessToken", userTokenState.getAccessToken());
                        editor.putString("userType", userTokenState.getRole());
                        editor.putLong("userId",userTokenState.getId());
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public boolean checkAllFields(String usernane, String password) {
        if (usernane.trim().length() == 0) {
            binding.usernameField.setError("Username is required!");
            return false;
        }
        if (password.trim().length() == 0) {
            binding.passwordField.setError("Password is required!");
            return false;
        }
        return true;
    }
}
