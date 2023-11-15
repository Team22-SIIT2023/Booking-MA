package com.example.booking_team22.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AmenityListAdapter;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.databinding.ActivityAccommodationDetailsBinding;
import com.example.booking_team22.databinding.ActivityHomeBinding;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.Notification;

import java.util.ArrayList;

public class AccommodationDetailsScreenActivity extends AppCompatActivity {


    private ArrayList<Amenity> amenities = new ArrayList<Amenity>();
    AmenityListAdapter adapter;

    ActivityAccommodationDetailsBinding binding;

    public AccommodationDetailsScreenActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        binding = ActivityAccommodationDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prepareProductList(amenities);
        adapter = new AmenityListAdapter(this, amenities);
        binding.amenityList.setAdapter(adapter);
    }



    private void prepareProductList(ArrayList<Amenity> amenities){
        amenities.add(new Amenity(
                1L,
                "Wifi",
                R.drawable.wifi
        ));
        amenities.add(new Amenity(
                1L,
                "Air conditioning",
                R.drawable.air_conditioning
        ));
        amenities.add(new Amenity(
                1L,
                "Air conditioning",
                R.drawable.air_conditioning
        ));
        amenities.add(new Amenity(
                1L,
                "Air conditioning",
                R.drawable.air_conditioning
        ));
        amenities.add(new Amenity(
                1L,
                "Air conditioning",
                R.drawable.air_conditioning
        ));
    }
}
