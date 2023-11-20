package com.example.booking_team22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.booking_team22.databinding.ActivityRegisterBinding;
import com.example.booking_team22.databinding.ActivitySplashScreenBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        String[] arraySpinner = new String[] {
                "Guest", "Host"
        };

        Spinner s = (Spinner) binding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        Button signUpButton = binding.signupButton;

        signUpButton.setOnClickListener(v ->{
            new AlertDialog.Builder(this)
                    .setTitle("Confirm registration")
                    .setMessage("We have sent an activation link to your email address." +
                            "\n" +
                            "The link is active for the next 24h.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_email)
                    .show();
        });

        setContentView(binding.getRoot());
    }
}