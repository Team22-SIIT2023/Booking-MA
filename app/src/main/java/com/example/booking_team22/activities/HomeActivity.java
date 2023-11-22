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

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.booking_team22.R;
import com.example.booking_team22.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private String userType;
//    public static final int FRAGMENT_ACCOMMODATIONS = R.id.nav_accomodations;
//    public static final int FRAGMENT_RESERVATIONS = R.id.nav_reservations;
//    public static final int FRAGMENT_NOTIFICATIONS = R.id.nav_notifications;
//    public static final int FRAGMENT_ACCOUNT = R.id.nav_account;
    private NavController navController;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Set<Integer> topLevelDestinations = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userType=getIntent().getStringExtra("userType");
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_accomodations,R.id.nav_reservations,R.id.nav_notifications,
                R.id.nav_account,R.id.nav_accommodations_approval,R.id.nav_reported_comments,
                R.id.nav_reported_users)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Objects.equals(userType, "guest")) {
            getMenuInflater().inflate(R.menu.guest_menu,menu);

        }if(Objects.equals(userType, "host")) {
            getMenuInflater().inflate(R.menu.guest_menu,menu);

        }if(Objects.equals(userType, "admin")) {

            getMenuInflater().inflate(R.menu.admin_menu,menu);
        }


        return true;

    }
}
