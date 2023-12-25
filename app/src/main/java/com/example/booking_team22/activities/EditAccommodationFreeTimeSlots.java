package com.example.booking_team22.activities;

import static com.example.booking_team22.clients.ClientUtils.accommodationService;
import static com.example.booking_team22.clients.ClientUtils.userService;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.booking_team22.R;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.ActivityEditAccommodationFreeTimeSlotsBinding;
import com.example.booking_team22.databinding.ActivityEditAccomodationBinding;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.PricelistItem;
import com.example.booking_team22.model.TimeSlot;
import com.example.booking_team22.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccommodationFreeTimeSlots extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    ActivityEditAccommodationFreeTimeSlotsBinding binding;
    private TextInputLayout accommodationPrice;
    private TextInputEditText cicoInput;
    private TextInputEditText cicoInput2;
    private TextInputEditText freeTimeSlotsStart;
    private TextInputEditText freeTimeSlotsEnd;

    private SharedPreferences sp;
    private Accomodation accommodation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_accommodation_free_time_slots);

        binding = ActivityEditAccommodationFreeTimeSlotsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);
        setDate(binding.freeTimeSlotsInput1);
        setDate(binding.freeTimeSlotsInput2);

        Button priceButton = binding.buttonSavePrice;
        Button freeTimeSlotsButton = binding.buttonSaveFreeTimeSlot;


        this.accommodationPrice = binding.accommodationPrice;
        this.cicoInput = binding.cicoInput;
        this.cicoInput2 = binding.cicoInput2;
        this.freeTimeSlotsStart = binding.freeTimeSlotsInput1;
        this.freeTimeSlotsEnd = binding.freeTimeSlotsInput2;


        Call<Accomodation> callUser = accommodationService.getById(1L); // Assuming you have a method in your UserApiClient to get a user by ID
        callUser.enqueue(new Callback<Accomodation>() {
            @Override
            public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                if (response.code() == 200) {
                    Log.d("USER", "Message received");
                    accommodation = response.body();
                } else {
                    Log.d("ACC_REQUEST", "Message received: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Accomodation> call, Throwable t) {
                Log.e("ACC_REQUEST", "Error: " + t.getMessage(), t);
            }
        });

        priceButton.setOnClickListener(v ->{

            String startDate = cicoInput.getText().toString();
            String endDate = cicoInput2.getText().toString();
            PricelistItem pricelist = new PricelistItem();
            TimeSlot timeslot = new TimeSlot(startDate, endDate);
            pricelist.setTimeSlot(timeslot);
            pricelist.setPrice(Integer.parseInt(accommodationPrice.getEditText().getText().toString()));

            Call<Accomodation> call = ClientUtils.accommodationService.editPrice(pricelist, 1L);

            call.enqueue(new Callback<Accomodation>() {
                @Override
                public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                    if (response.code() == 200){
                        Log.d("REZ","Meesage recieved");
                        System.out.println(response.body());
                    }else{
                        Log.d("REZ","Meesage recieved: "+response.code());
                    }
                }

                @Override
                public void onFailure(Call<Accomodation> call, Throwable t) {
                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                }
            });
        });

        freeTimeSlotsButton.setOnClickListener(v ->{
            String startDate = freeTimeSlotsStart.getText().toString();
            String endDate = freeTimeSlotsEnd.getText().toString();
            TimeSlot timeslot = new TimeSlot(startDate, endDate);
            Call<Accomodation> call = ClientUtils.accommodationService.editFreeTimeslots(timeslot, 1L);

            call.enqueue(new Callback<Accomodation>() {
                @Override
                public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                    if (response.code() == 200){
                        Log.d("REZ","Meesage recieved");
                        System.out.println(response.body());
                    }else{
                        Log.d("REZ","Meesage recieved: "+response.code());
                    }
                }

                @Override
                public void onFailure(Call<Accomodation> call, Throwable t) {
                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                }
            });
        });
    }

    private void setDate(TextInputEditText input) {
        input.setOnClickListener(v->{
            final Calendar c = Calendar.getInstance();

            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            String formattedDate = String.format(Locale.US, "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                            input.setText(formattedDate);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }
}