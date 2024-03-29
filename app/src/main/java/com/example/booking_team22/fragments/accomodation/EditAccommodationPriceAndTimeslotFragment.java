package com.example.booking_team22.fragments.accomodation;

import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.accommodationService;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.FragmentEditAccommodationPriceAndTimeslotBinding;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.PricelistItem;
import com.example.booking_team22.model.TimeSlot;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditAccommodationPriceAndTimeslotFragment extends Fragment {
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextInputLayout accommodationPrice;
    private TextInputEditText cicoInput;
    private TextInputEditText cicoInput2;
    private TextInputEditText freeTimeSlotsStart;
    private TextInputEditText freeTimeSlotsEnd;
    private SharedPreferences sp;
    private String accessToken;
    private Accomodation accommodation;

    FragmentEditAccommodationPriceAndTimeslotBinding binding;

    public EditAccommodationPriceAndTimeslotFragment() {
    }

    public static EditAccommodationPriceAndTimeslotFragment newInstance(Accomodation accommodation) {
        EditAccommodationPriceAndTimeslotFragment fragment = new EditAccommodationPriceAndTimeslotFragment();
        Bundle args = new Bundle();
        args.putParcelable("accommodation", accommodation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.accommodation = args.getParcelable("accommodation");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditAccommodationPriceAndTimeslotBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();

        sp = getActivity().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);
        setDate(binding.freeTimeSlotsInput1);
        setDate(binding.freeTimeSlotsInput2);

        return root;
    }

    private void setDate(TextInputEditText input) {
        input.setOnClickListener(v->{
            final Calendar c = Calendar.getInstance();

            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button priceButton = binding.buttonSavePrice;
        Button freeTimeSlotsButton = binding.buttonSaveFreeTimeSlot;

        this.accommodationPrice = binding.accommodationPrice;
        this.cicoInput = binding.cicoInput;
        this.cicoInput2 = binding.cicoInput2;
        this.freeTimeSlotsStart = binding.freeTimeSlotsInput1;
        this.freeTimeSlotsEnd = binding.freeTimeSlotsInput2;

        priceButton.setOnClickListener(v ->{

            String startDate = cicoInput.getText().toString();
            String endDate = cicoInput2.getText().toString();
            PricelistItem pricelist = new PricelistItem();
            TimeSlot timeslot = new TimeSlot(startDate, endDate);
            pricelist.setTimeSlot(timeslot);
            pricelist.setPrice(Integer.parseInt(accommodationPrice.getEditText().getText().toString()));

            Call<Accomodation> call = ClientUtils.accommodationService.editPrice("Bearer " + accessToken ,pricelist, accommodation.getId());

            call.enqueue(new Callback<Accomodation>() {
                @Override
                public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                    if (response.code() == 200){
                        Log.d("REZ","Meesage recieved");
                        System.out.println(response.body());
                        Toast.makeText(getActivity(),"Successfully updated!", Toast.LENGTH_SHORT).show();
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
            Call<Accomodation> call = ClientUtils.accommodationService.editFreeTimeslots("Bearer " + accessToken, timeslot, accommodation.getId());

            call.enqueue(new Callback<Accomodation>() {
                @Override
                public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                    if (response.code() == 200){
                        Log.d("REZ","Meesage recieved");
                        System.out.println(response.body());
                        Toast.makeText(getActivity(),"Successfully updated!", Toast.LENGTH_SHORT).show();
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
}