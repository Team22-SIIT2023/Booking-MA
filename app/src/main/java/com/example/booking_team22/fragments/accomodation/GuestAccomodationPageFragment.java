package com.example.booking_team22.fragments.accomodation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.databinding.AccomodationCardBinding;
import com.example.booking_team22.databinding.FragmentAccomodationPageBinding;
import com.example.booking_team22.model.Accomodation;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class GuestAccomodationPageFragment extends ListFragment {
    private int mYear, mMonth, mDay, mHour, mMinute;

    public static ArrayList<Accomodation> products = new ArrayList<Accomodation>();
    AccomodationListAdapter adapter;
    private FragmentAccomodationPageBinding binding;

    public static GuestAccomodationPageFragment newInstance() {
        return new GuestAccomodationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        products = new ArrayList<Accomodation>();

        binding = FragmentAccomodationPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);


        prepareProductList(products);
        adapter = new AccomodationListAdapter(getActivity(), products);
        setListAdapter(adapter);

        Button btnFilters = binding.btnFilters;
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
            View dialogView = getLayoutInflater().inflate(R.layout.filter_dialog, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });

        return root;
    }

    private void setDate(TextInputEditText input) {
        input.setOnClickListener(v->{
            final Calendar c = Calendar.getInstance();

            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            input.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareProductList(ArrayList<Accomodation> products){
        products.add(new Accomodation(
                1L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap1));
        products.add(new Accomodation(
                2L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap2));
        products.add(new Accomodation(
                3L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap4));
        products.add(new Accomodation(
                4L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap5));
        products.add(new Accomodation(
                5L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap6));
    }
}
