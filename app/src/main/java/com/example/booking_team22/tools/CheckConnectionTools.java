package com.example.booking_team22.tools;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.booking_team22.activities.HomeActivity;
import com.example.booking_team22.activities.SplashScreenActivity;

public class CheckConnectionTools {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Toast.makeText(context, "You are connect to WIFI", Toast.LENGTH_LONG).show();
                    return TYPE_WIFI;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Toast.makeText(context, "You are connect to Mobile network", Toast.LENGTH_LONG).show();
                    return TYPE_MOBILE;
                }
            }

        Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
        return TYPE_NOT_CONNECTED;
    }

    public static int calculateTimeTillNextSync(int minutes) {
        return 1000 * 60 * minutes;
    }

}