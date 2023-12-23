package com.example.booking_team22.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.booking_team22.R;
import com.example.booking_team22.databinding.ActivityHomeBinding;
import com.example.booking_team22.databinding.FragmentAccommodationDetailBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private String userType;

    private NavController navController;
    private Toolbar toolbar;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp= getApplicationContext().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        userType=sp.getString("userType","");
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_accomodations,R.id.nav_reservations,R.id.nav_notifications,
                R.id.nav_account,R.id.nav_accommodations_approval,R.id.nav_reported_comments,
                R.id.nav_reported_users,R.id.nav_details,R.id.nav_createAccommodation)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        if (item.getItemId() == R.id.nav_accomodations) {
            navController.popBackStack(navController.getGraph().getStartDestination(), false);
            return true;
        }
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Objects.equals(userType, "ROLE_GUEST")) {
            getMenuInflater().inflate(R.menu.guest_menu,menu);

        }if(Objects.equals(userType, "ROLE_HOST")) {
            getMenuInflater().inflate(R.menu.host_menu,menu);

        }if(Objects.equals(userType, "ROLE_ADMIN")) {

            getMenuInflater().inflate(R.menu.admin_menu,menu);
        }
        return true;

    }
}
