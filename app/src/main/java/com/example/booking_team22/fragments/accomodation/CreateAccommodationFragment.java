package com.example.booking_team22.fragments.accomodation;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.booking_team22.R;
import com.example.booking_team22.databinding.ActivityEditAccomodationBinding;
import com.example.booking_team22.databinding.FragmentCreateAccommodationBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CreateAccommodationFragment extends Fragment {

    private int mYear, mMonth, mDay;
    FragmentCreateAccommodationBinding binding;

    public CreateAccommodationFragment() {
    }

    public static CreateAccommodationFragment newInstance(String param1, String param2) {
        CreateAccommodationFragment fragment = new CreateAccommodationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateAccommodationBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);

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

                            input.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        Button btnFilters = binding.btnFiltersEdit;
        btnFilters.setOnClickListener(v -> {
            Log.i("travelBee", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
            View dialogView = getLayoutInflater().inflate(R.layout.filter_dialog, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });
    }
}