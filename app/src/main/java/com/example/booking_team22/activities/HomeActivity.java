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
import com.example.booking_team22.databinding.ActivityLoginBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    public static final int FRAGMENT_ACCOMMODATIONS = R.id.nav_accomodations;
    public static final int FRAGMENT_RESERVATIONS = R.id.nav_reservations;
    public static final int FRAGMENT_NOTIFICATIONS = R.id.nav_notifications;
    public static final int FRAGMENT_ACCOUNT = R.id.nav_account;
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

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_accomodations,R.id.nav_reservations,R.id.nav_notifications)
                .build();

        //NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        int fragmentId = getIntent().getIntExtra("fragmentId", 0);
        if (fragmentId != 0) {
            navController.navigate(fragmentId);
        }
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
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }
}