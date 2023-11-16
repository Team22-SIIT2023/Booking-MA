package com.example.booking_team22.fragments.reservations.guestReservationTabs;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.GuestRequestAdapter;
import com.example.booking_team22.databinding.FragmentGuestRequestsBinding;
import com.example.booking_team22.model.Reservation;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestRequestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestRequestsFragment extends ListFragment {
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    GuestRequestAdapter adapter;
    FragmentGuestRequestsBinding binding;
    public GuestRequestsFragment() {
        // Required empty public constructor
    }
    public static GuestRequestsFragment newInstance(String param1, String param2) {
        GuestRequestsFragment fragment = new GuestRequestsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGuestRequestsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareReservationsList(reservations);
        adapter = new GuestRequestAdapter(getActivity(), reservations);
        setListAdapter(adapter);

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);

        Button btnFilters = binding.btnFiltersRequest;

        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
            View dialogView = getLayoutInflater().inflate(R.layout.filter_requests, null);
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


    private void prepareReservationsList(ArrayList<Reservation> reservations){
        reservations.add(new Reservation(
                1L,
                "Accomodation name",
                "Address",
                R.drawable.ap2,
                "15-5-2023-20-5-2023",
                R.drawable.ic_cancel
        ));
        reservations.add(new Reservation(
                2L,
                "Accomodation name",
                "Address",
                R.drawable.ap1,
                "15-5-2023-26-5-2023",
                R.drawable.ic_aacept
        ));
        reservations.add(new Reservation(
                2L,
                "Accomodation name",
                "Address",
                R.drawable.ap1,
                "15-5-2023-26-5-2023",
                R.drawable.ic_loading
        ));
    }
}