package com.example.booking_team22.adapters;

import static com.example.booking_team22.clients.ClientUtils.notificationService;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.HostNotificationSettings;
import com.example.booking_team22.model.Notification;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationListAdapter extends ArrayAdapter<Notification> {
    private ArrayList<Notification> aNotifications;
    private SharedPreferences sp;
    private String accessToken;

    public NotificationListAdapter(FragmentActivity context, ArrayList<Notification> notifications){
        super(context, R.layout.notification_card, notifications);
        aNotifications = notifications;
        sp = context.getApplicationContext().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);

        accessToken = sp.getString("accessToken", "");
    }

    @Override
    public int getCount() {
        return aNotifications.size();
    }

    @Nullable
    @Override
    public Notification getItem(int position) {
        return aNotifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Notification notification = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_card,
                    parent, false);
        }
        LinearLayout notificationCard = convertView.findViewById(R.id.notification_card_item);
        TextView notificationDate = convertView.findViewById(R.id.notification_date);
        TextView notificationTitle = convertView.findViewById(R.id.notification_title);
        TextView notificationDescription = convertView.findViewById(R.id.notification_message);

        Button readBtn = convertView.findViewById(R.id.notificationButton);
        readBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notification2,0,0,0);

        readBtn.setOnClickListener(v -> {
            notification.setRead(true);
            Call<Notification> call = notificationService.updateNotification("Bearer "+accessToken,notification.getId(),notification);
            call.enqueue(new Callback<Notification>() {
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    if (response.code() == 200) {
                        System.out.println(response.body());
                        Log.d("COMMENTS", "Meesage recieved");
                    } else {
                        Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<Notification> call, Throwable t) {
                    Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                }
            });
        });

        if(notification != null){
            notificationDate.setText(notification.getDate());
            notificationTitle.setText(notification.getType().name());
            notificationDescription.setText(notification.getText());
            notificationCard.setOnClickListener(v -> {
            });
        }
        return convertView;
    }
}
