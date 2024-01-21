package com.example.booking_team22.fragments.account;

import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.userService;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.booking_team22.R;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.FragmentAccountBinding;
import com.example.booking_team22.databinding.FragmentCreateAccommodationBinding;
import com.example.booking_team22.model.AccommodationStatus;
import com.example.booking_team22.model.AccommodationType;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Account;
import com.example.booking_team22.model.Address;
import com.example.booking_team22.model.Host;
import com.example.booking_team22.model.PricelistItem;
import com.example.booking_team22.model.Role;
import com.example.booking_team22.model.Status;
import com.example.booking_team22.model.TimeSlot;
import com.example.booking_team22.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private TextView name;
    private TextView lastName;
    private TextView country;
    private TextView city;
    private TextView address;
    private TextView phoneNumber;
    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private Button saveChanges;
    private Button deleteUser;
    private User updatedUser;
    FragmentAccountBinding binding;
    private SharedPreferences sp;
    private String userType;

    public AccountFragment() {
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        this.name = binding.name;
        this.lastName = binding.lastName;
        this.country = binding.country;
        this.city = binding.city;
        this.address = binding.address;
        this.phoneNumber = binding.phoneNumber;
        this.email = binding.email;
        this.password = binding.password;
        this.confirmPassword = binding.password1;
        this.saveChanges = binding.saveChanges;
        this.deleteUser = binding.deleteAccount;

        sp= getActivity().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        userType=sp.getString("userType","");
        long id=sp.getLong("userId",0L);
        if(userType.equals("ROLE_ADMIN")){
            this.deleteUser.setVisibility(View.GONE);
        }
        Call<User> callUser = userService.getUser(id); // Assuming you have a method in your UserApiClient to get a user by ID
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Log.d("USER", "Message received");
                    updatedUser = response.body();
                    name.setText(updatedUser.getFirstName());
                    lastName.setText(updatedUser.getLastName());
                    country.setText(updatedUser.getAddress().getCountry());
                    city.setText(updatedUser.getAddress().getCity());
                    address.setText(updatedUser.getAddress().getAddress());
                    phoneNumber.setText(updatedUser.getPhoneNumber());
                    email.setText(updatedUser.getAccount().getUsername());
                    password.setText(updatedUser.getAccount().getPassword());
                    confirmPassword.setText(updatedUser.getAccount().getPassword());
                } else {
                    Log.d("USER_REQUEST", "Message received: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("USER_REQUEST", "Error: " + t.getMessage(), t);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<User> call;
                updateUser();

                Log.d(updatedUser.getFirstName(), updatedUser.getLastName());
                Log.d(updatedUser.getId().toString(), updatedUser.getLastName());
                call = ClientUtils.userService.updateUser(updatedUser,updatedUser.getId());

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 201){
                            System.out.println(response.body());
                            User userAfter = response.body();
                            System.out.println(userAfter);
                            getActivity().getSupportFragmentManager().popBackStack();
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<User> call;
                updateUser();

                Log.d(updatedUser.getFirstName(), updatedUser.getLastName());
                Log.d(updatedUser.getId().toString(), updatedUser.getLastName());
                call = ClientUtils.userService.deleteUser(updatedUser.getId());

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 201){
                            System.out.println(response.body());
                            User userAfter = response.body();
                            System.out.println(userAfter);
                            getActivity().getSupportFragmentManager().popBackStack();
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });
    }

    private User updateUser() {
        String name = this.name.getText().toString();
        String lastName = this.lastName.getText().toString();
        String country = this.country.getText().toString();
        String city = this.city.getText().toString();
        String address = this.city.getText().toString();
        String email = this.email.getText().toString();
        String phoneNumber = this.phoneNumber.getText().toString();
        String password = this.password.getText().toString();
        String confirmPassword = this.confirmPassword.getText().toString();

        if (name.length() == 0
                && lastName.length() == 0
                && country.length() == 0
                && city.length() == 0
                && address.length() == 0
                && email.length() == 0
                && phoneNumber.length() == 0
                && password.length() == 0
                && confirmPassword.length() == 0
                && (!password.equals(confirmPassword))) {
            return null;
        }
        Address userAddress = new Address(address,country,city);
        Account userAccount = new Account(email,password,updatedUser.getAccount().getStatus(), updatedUser.getAccount().getRoles());
        updatedUser.setFirstName(name);
        updatedUser.setLastName(lastName);
        updatedUser.setAddress(userAddress);
        updatedUser.setAccount(userAccount);
        updatedUser.setPhoneNumber(phoneNumber);
        return updatedUser;
    }


}