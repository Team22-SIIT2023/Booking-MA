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
import com.example.booking_team22.model.Reservation;

import java.util.ArrayList;

public class GuestRequestAdapter extends ArrayAdapter {
    private ArrayList<Reservation> aReservations;

    public GuestRequestAdapter(FragmentActivity context, ArrayList<Reservation> reservations){
        super(context, R.layout.guest_request_card, reservations);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.guest_request_card,
                    parent, false);
        }
        LinearLayout reservationCard = convertView.findViewById(R.id.request_card_item);
        TextView reservationTitle = convertView.findViewById(R.id.reservation_title);
        TextView reservationAddress = convertView.findViewById(R.id.apartment_address);
        ImageView imageView = convertView.findViewById(R.id.apartment_icon);
        TextView resevationDate = convertView.findViewById(R.id.reservation_dates);
        ImageView imageView2 = convertView.findViewById(R.id.status_icons);


        if(reservation != null){
            reservationTitle.setText(reservation.getTitle());
            reservationAddress.setText(reservation.getAddress());
            imageView.setImageResource(reservation.getImage());
            imageView2.setImageResource(reservation.getIcon());
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
