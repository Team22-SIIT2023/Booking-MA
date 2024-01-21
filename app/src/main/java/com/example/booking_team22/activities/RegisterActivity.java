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
    EditText username, firstName, lastName, address, password, repeatPassword, phoneNumber, city, country;
    String role;
    User user;
    Spinner selectRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        String[] arraySpinner = new String[] {
                "Guest", "Host"
        };

        selectRole = (Spinner) binding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectRole.setAdapter(adapter);

        Button signUpButton = binding.signupButton;

        this.username = binding.emailField;
        this.password = binding.passwordField;
        this.repeatPassword = binding.passwordRepeatField;
        this.firstName = binding.firstNameField;
        this.lastName = binding.lastNameField;
        this.address = binding.adressField;
        this.city = binding.cityField;
        this.country = binding.countryField;
        this.phoneNumber = binding.phoneField;

        signUpButton.setOnClickListener(v ->{
            boolean isAllFieldsChecked = checkAllFields();

            if (isAllFieldsChecked) {
                User createdUser = createUser();

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
            }
        });


        setContentView(binding.getRoot());
    }

    public boolean checkAllFields() {
        if (this.username.getText().toString().length() == 0) {
            this.username.setError("This field is required!");
            return false;
        }
        if (this.password.getText().toString().length() == 0) {
            this.password.setError("This field is required!");
            return false;
        }
        if (this.repeatPassword.getText().toString().length() == 0) {
            this.repeatPassword.setError("This field is required!");
            return false;
        }
        if (!this.password.getText().toString().equals(this.repeatPassword.getText().toString())) {
            this.repeatPassword.setError("Passwords don't match!");
            return false;
        }
        if (this.firstName.getText().toString().length() == 0) {
            this.firstName.setError("This field is required!");
            return false;
        }
        if (this.lastName.getText().toString().length() == 0) {
            this.lastName.setError("This field is required!");
            return false;
        }
        if (this.address.getText().toString().length() == 0) {
            this.address.setError("This field is required!");
            return false;
        }
        if (this.city.getText().toString().length() == 0) {
            this.city.setError("This field is required!");
            return false;
        }
        if (this.country.getText().toString().length() == 0) {
            this.country.setError("This field is required!");
            return false;
        }
        if (this.phoneNumber.getText().toString().length() == 0) {
            this.phoneNumber.setError("This field is required!");
            return false;
        }
        return true;
    }

    public User createUser() {
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
        String role = getRole();
        userRole.setName(role);
        roles.add(userRole);
        account.setRoles(roles);

        createdUser.setAccount(account);

        Address address = new Address();
        address.setAddress(this.address.getText().toString());
        address.setCity(this.city.getText().toString());
        address.setCountry(this.country.getText().toString());
        createdUser.setAddress(address);

        return createdUser;
    }

    public String getRole() {
        role = selectRole.getSelectedItem().toString();
        if (role.equals("Guest")) {
            return "ROLE_GUEST";
        }
        return "ROLE_HOST";
    }
}