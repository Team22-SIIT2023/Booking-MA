package com.example.booking_team22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.booking_team22.R;
import com.example.booking_team22.databinding.ActivitySplashScreenBinding;
import com.example.booking_team22.tools.CheckConnectionTools;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    public static int TYPE_NOT_CONNECTED = 0;

    ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView imageView = binding.logo;

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        imageView.startAnimation(animation);

        int checking = CheckConnectionTools.getConnectivityStatus(this);

        //pitati!
        if (checking == TYPE_NOT_CONNECTED) {
            finish();
        }

        int SPLASH_TIME_OUT = 5000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);

                startActivity(intent);

                finish();
            }
            }, SPLASH_TIME_OUT);
    }
}