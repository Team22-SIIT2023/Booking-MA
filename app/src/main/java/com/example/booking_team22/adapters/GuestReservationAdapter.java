package com.example.booking_team22.adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.requestService;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
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
import com.example.booking_team22.model.Notification;
import com.example.booking_team22.model.Reservation;
import com.example.booking_team22.model.ReservationRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestReservationAdapter extends ArrayAdapter {
    private ArrayList<ReservationRequest> aReservations;


    public GuestReservationAdapter(FragmentActivity context, ArrayList<ReservationRequest> reservations){
        super(context, R.layout.guest_request_card, reservations);
        aReservations = reservations;

    }

    @Override
    public int getCount() {
        return aReservations.size();
    }

    @Nullable
    @Override
    public ReservationRequest getItem(int position) {
        return aReservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReservationRequest reservation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.guest_request_card,
                    parent, false);
        }
        LinearLayout reservationCard = convertView.findViewById(R.id.request_card_item);

        TextView timeSlot = convertView.findViewById(R.id.textViewTimeSlot);
        TextView price = convertView.findViewById(R.id.textViewPrice);
        TextView guest = convertView.findViewById(R.id.textViewGuest);
        TextView guestNum = convertView.findViewById(R.id.textViewGuestNumber);
        TextView status = convertView.findViewById(R.id.textViewStatus);
        TextView accommodation = convertView.findViewById(R.id.textViewAccommodation);


        if(reservation != null){
            timeSlot.setText(reservation.getTimeSlot().getStartDate()+"-"+reservation.getTimeSlot().getEndDate());
            price.setText(String.valueOf((reservation.getPrice())));
            guest.setText(String.valueOf(reservation.getGuest().getFirstName()));
            guestNum.setText(String.valueOf(reservation.getGuestNumber()));
            status.setText(reservation.getStatus().name());
            accommodation.setText(reservation.getAccommodation().getName());

        }
        return convertView;
    }
}
