package com.example.booking_team22.adapters;

import static com.example.booking_team22.clients.ClientUtils.userService;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import static android.content.Context.MODE_PRIVATE;

import static com.example.booking_team22.clients.ClientUtils.requestService;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.booking_team22.R;
import com.example.booking_team22.model.RequestStatus;
import com.example.booking_team22.model.Reservation;
import com.example.booking_team22.model.ReservationRequest;
import com.example.booking_team22.model.User;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestRequestAdapter extends ArrayAdapter {
    private ArrayList<ReservationRequest> aReservations;
    private Button reportGuestButton;
    private FragmentActivity context;
    private ReservationRequest reservation;
    private String reportText;
    private SharedPreferences sp;
    private String accessToken;
    private String userType;

    public GuestRequestAdapter(FragmentActivity context, ArrayList<ReservationRequest> reservations){
        super(context, R.layout.guest_request_card, reservations);
        this.context = context;
        aReservations = reservations;
        sp= context.getApplicationContext().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        accessToken=sp.getString("accessToken","");
        userType=sp.getString("userType","");

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
        reservation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.guest_request_card,
                    parent, false);
        }
        LinearLayout reservationCard = convertView.findViewById(R.id.request_card_item);
        Button deleteRequest=convertView.findViewById(R.id.deleteRequest);
        TextView timeSlot = convertView.findViewById(R.id.textViewTimeSlot);
        TextView price = convertView.findViewById(R.id.textViewPrice);
        TextView guest = convertView.findViewById(R.id.textViewGuest);
        TextView guestLabel = convertView.findViewById(R.id.labelGuest);
        TextView guestCancellations = convertView.findViewById(R.id.textViewCancellations);
        TextView cancellationsLabel = convertView.findViewById(R.id.labelCancellations);
        if(userType.equals("ROLE_GUEST")){
            guest.setVisibility(View.GONE);
            guestLabel.setVisibility(View.GONE);
            guestCancellations.setVisibility(View.GONE);
            cancellationsLabel.setVisibility(View.GONE);
        }else{
            deleteRequest.setVisibility(View.GONE);
        }
        TextView guestNum = convertView.findViewById(R.id.textViewGuestNumber);
        TextView status = convertView.findViewById(R.id.textViewStatus);
        TextView accommodation = convertView.findViewById(R.id.textViewAccommodation);

        reportGuestButton = convertView.findViewById(R.id.reportGuestButton);
        reportGuestButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_report_user,0,0,0);

        reportGuestButton.setOnClickListener(v ->{
            showReportDialog();
        });


        if(reservation != null){
            timeSlot.setText("start:"+reservation.getTimeSlot().getStartDate()+"\nend:"+reservation.getTimeSlot().getEndDate());
            price.setText(String.valueOf((reservation.getPrice())));
            guest.setText(String.valueOf(reservation.getGuest().getAccount().getUsername()));
            guestNum.setText(String.valueOf(reservation.getGuestNumber()));
            status.setText(reservation.getStatus().name());
            accommodation.setText(reservation.getAccommodation().getName());

            if(userType.equals("ROLE_GUEST")) {
                if (reservation.getStatus() == RequestStatus.PENDING) {
                    deleteRequest.setOnClickListener(v -> {
                        Log.d("DELETE_REQUEST", "Button Clicked");
                        Call<ReservationRequest> call = requestService.deleteRequest("Bearer " + accessToken, reservation.getId());
                        call.enqueue(new Callback<ReservationRequest>() {
                            @Override
                            public void onResponse(Call<ReservationRequest> call, Response<ReservationRequest> response) {
                                if (response.isSuccessful()) {
                                    ReservationRequest deletedRequest = response.body();
                                } else {
                                    Log.d("DELETE_ERROR", "Error code: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<ReservationRequest> call, Throwable t) {
                                Log.e("DELETE_ERROR", "Error: " + t.getMessage(), t);
                            }
                        });
                    });
                }
            }else{
                Call<Integer> call = requestService.getCancellationsForGuest("Bearer " + accessToken, reservation.getGuest().getId());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            int numberOfCancellations = response.body();
                            guestCancellations.setText(String.valueOf(numberOfCancellations));
                        } else {
                            Log.d("Cancellations error", "Error code: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e("Cancellations error", "Error: " + t.getMessage(), t);
                    }
                });
            }



        }
        return convertView;
    }

    public void showReportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Reason");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reportText = input.getText().toString();
                reservation.getGuest().setReportingReason(reportText);
                reportHost();
            }
        });

        builder.create();
        builder.show();
    }


    public void reportHost() {

        Call<User> callCreateComment = userService.reportUser(reservation.getGuest(), reservation.getAccommodation().getHost().getId());
        callCreateComment.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    Toast.makeText(context,"Reported!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context,"You can't report!", Toast.LENGTH_SHORT).show();
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }
}
