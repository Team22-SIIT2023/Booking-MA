package com.example.booking_team22.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.booking_team22.R;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Notification;

import java.util.ArrayList;

public class NotificationListAdapter extends ArrayAdapter<Notification> {
    private ArrayList<Notification> aNotifications;

    public NotificationListAdapter(FragmentActivity context, ArrayList<Notification> notifications){
        super(context, R.layout.notification_card, notifications);
        aNotifications = notifications;
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
