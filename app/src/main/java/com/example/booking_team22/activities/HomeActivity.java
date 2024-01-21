package com.example.booking_team22.activities;

import static com.example.booking_team22.clients.ClientUtils.notificationService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.clients.ForegroundService;
import com.example.booking_team22.databinding.ActivityHomeBinding;
import com.example.booking_team22.databinding.FragmentAccommodationDetailBinding;
import com.example.booking_team22.model.Notification;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private String userType;

    private NavController navController;
    private Toolbar toolbar;
    private SharedPreferences sp;

    private boolean runThread;

    private Notification newNotification;
    private static String CHANNEL_ID = "Zero channel";

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
                R.id.nav_reported_users,R.id.nav_details,R.id.nav_createAccommodation,
                R.id.nav_reports,R.id.nav_settings, R.id.nav_login)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        createNotificationChannel();

        runThread=true;
        Thread thread=new Thread(()->{
            while (runThread){
                try{
                    Thread.sleep(3000);
                    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    SharedPreferences sp = getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);
                    String accessToken = sp.getString("accessToken", "");
                    String userType = sp.getString("userType","");

                    long id = sp.getLong("userId",0L);

                    Call<Notification> call = notificationService.getNewNotifications("Bearer "+accessToken,id);
                    call.enqueue(new Callback<Notification>() {
                        @Override
                        public void onResponse(Call<Notification> call, Response<Notification> response) {
                            if (response.code() == 200) {
                                Log.d("COMMENTS", "Meesage recieved");
                                newNotification=response.body();

                            } else {
                                Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<Notification> call, Throwable t) {
                            Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                        }
                    });

                    if(newNotification!=null){
                        Thread.sleep(3000);
                        Intent intent = new Intent(this, ForegroundService.class);
                        intent.setAction(ForegroundService.ACTION_START_FOREGROUND_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Objects.requireNonNull(this).startForegroundService(intent);
                        } else {
                            Objects.requireNonNull(this).startService(intent);
                        }
                    }
                    newNotification=null;

                }catch (Exception ex){}
            }

        });
        thread.start();

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
