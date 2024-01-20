package com.example.booking_team22.adapters;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.booking_team22.R;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Status;
import com.example.booking_team22.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListAdapter extends ArrayAdapter<User> implements SensorEventListener {
    ArrayList<User> aUsers;

    private String accessToken;

    private SharedPreferences sp;
    private String userType;

    private SensorManager sensorManager;

    public UserListAdapter(FragmentActivity context, ArrayList<User> users){
        super(context, R.layout.fragment_reported_user, users);
        aUsers = users;
        sp = context.getApplicationContext().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");
        userType = sp.getString("userType","");
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public int getCount() {
        return aUsers.size();
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return aUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reported_user_card,
                    parent, false);
        }
        LinearLayout reportedUserCard = convertView.findViewById(R.id.reported_user_card_item);
        TextView reportedUser = convertView.findViewById(R.id.user_email);
        ImageView reportedUserPicture = convertView.findViewById(R.id.user_icon);
        Button blockButton = convertView.findViewById(R.id.blockUser);

        if(user != null){
            reportedUser.setText(user.getAccount().getUsername());

            blockButton.setOnClickListener(v->{
                blockUser(position);
            });
        }



        return convertView;
    }

    public void blockUser(int position) {
        User user = aUsers.get(position);
        Call<User> callBlockUser = ClientUtils.userService.blockUser("Bearer " + accessToken, user.getId(),user);
        callBlockUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    System.out.println(response.body());
                    // Dodajte kod za blokiranje korisnika ovde
                } else {
                    Log.d("User blocking failed", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("User request", "Error: " + t.getMessage(), t);
            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = sensorEvent.values;
            float x = values[0];

            float tiltThreshold = -5.0f;

            if (x < tiltThreshold) {
                Log.d("Blokira korisnika","Senzor");
                blockUser(0);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
