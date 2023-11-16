package com.example.booking_team22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.booking_team22.R;
import com.example.booking_team22.databinding.ActivityEditAccomodationBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class EditAccomodationActivity extends AppCompatActivity {
    private int mYear, mMonth, mDay, mHour, mMinute;
    ActivityEditAccomodationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_accomodation);

        binding = ActivityEditAccomodationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);
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

                            input.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        Button btnFilters = binding.btnFiltersEdit;
        btnFilters.setOnClickListener(v -> {
            Log.i("travelBee", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            View dialogView = getLayoutInflater().inflate(R.layout.filter_dialog, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });
    }
}