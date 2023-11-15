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
import com.example.booking_team22.model.Notification;
import com.example.booking_team22.model.Reservation;

import java.util.ArrayList;

public class GuestReservationAdapter extends ArrayAdapter {
    private ArrayList<Reservation> aReservations;

    public GuestReservationAdapter(FragmentActivity context, ArrayList<Reservation> reservations){
        super(context, R.layout.reservation_card, reservations);
        aReservations = reservations;
    }

    @Override
    public int getCount() {
        return aReservations.size();
    }

    @Nullable
    @Override
    public Reservation getItem(int position) {
        return aReservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Reservation reservation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_card,
                    parent, false);
        }
        LinearLayout reservationCard = convertView.findViewById(R.id.reservation_card_item);
        TextView reservationTitle = convertView.findViewById(R.id.reservation_title);
        TextView reservationAddress = convertView.findViewById(R.id.apartment_address);
        ImageView imageView = convertView.findViewById(R.id.apartment_icon);
        TextView resevationDate = convertView.findViewById(R.id.reservation_dates);

        if(reservation != null){
            reservationTitle.setText(reservation.getTitle());
            reservationAddress.setText(reservation.getAddress());
            imageView.setImageResource(reservation.getImage());
            resevationDate.setText(reservation.getDates());
            reservationCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + reservation.getTitle() + ", id: " +
                        reservation.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + reservation.getTitle()  +
                        ", id: " + reservation.getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }
        return convertView;
    }
}
