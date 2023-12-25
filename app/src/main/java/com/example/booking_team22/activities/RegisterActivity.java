package com.example.booking_team22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.ActivityRegisterBinding;
import com.example.booking_team22.databinding.ActivitySplashScreenBinding;
import com.example.booking_team22.model.Account;
import com.example.booking_team22.model.Address;
import com.example.booking_team22.model.Role;
import com.example.booking_team22.model.Status;
import com.example.booking_team22.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    EditText username, firstName, lastName, address, password, repeatPassword, phoneNumber;
    String role;
    User user;

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

        this.username = binding.emailField;
        this.password = binding.passwordField;
        this.repeatPassword = binding.passwordRepeatField;
        this.firstName = binding.firstNameField;
        this.lastName = binding.lastNameField;
        this.address = binding.adressField;
        this.phoneNumber = binding.phoneField;
        this.role = (String) s.getSelectedItem();


        signUpButton.setOnClickListener(v ->{
            User createdUser = new User();
            createdUser.setFirstName(this.firstName.getText().toString());
            createdUser.setLastName(this.lastName.getText().toString());
            createdUser.setPhoneNumber(this.phoneNumber.getText().toString());
            Account account = new Account();
            account.setUsername(this.username.getText().toString());
            account.setPassword(this.password.getText().toString());
            account.setStatus(Status.ACTIVE);
            List<Role> roles = new ArrayList<>();
            Role userRole = new Role();
            userRole.setName("ROLE_GUEST");
            roles.add(userRole);
            account.setRoles(roles);
            createdUser.setAccount(account);

            Address address = new Address();
            address.setAddress(this.address.getText().toString());
            address.setCity("Novi Sad");
            address.setCountry("Serbia");

            createdUser.setAddress(address);


            Call<User> call = ClientUtils.userService.signup(createdUser);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200){
                        Log.d("REZ","Meesage recieved");
                        System.out.println(response.body());
                        user = response.body();
                    }else{
                        Log.d("REZ","Meesage recieved: "+response.code());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                }
            });



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