package com.example.booking_team22.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.booking_team22.R;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.User;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<User> {
    ArrayList<User> aUsers;
    public UserListAdapter(FragmentActivity context, ArrayList<User> users){
        super(context, R.layout.fragment_reported_user, users);
        aUsers = users;
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

        if(user != null){
            reportedUser.setText(user.getAccount().getUsername());
//            reportedUserPicture.setImageResource(R.drawable.user);
        }
        return convertView;
    }
}
