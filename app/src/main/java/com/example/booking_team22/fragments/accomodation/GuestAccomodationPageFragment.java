package com.example.booking_team22.fragments.accomodation;

import static com.example.booking_team22.clients.ClientUtils.accommodationService;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.AccomodationCardBinding;
import com.example.booking_team22.databinding.FragmentAccomodationPageBinding;
import com.example.booking_team22.model.AccommodationType;
import com.example.booking_team22.model.Accomodation;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestAccomodationPageFragment extends ListFragment {
    private int mYear, mMonth, mDay, mHour, mMinute;

    public static ArrayList<Accomodation> products = new ArrayList<Accomodation>();
    AccomodationListAdapter adapter;
    private FragmentAccomodationPageBinding binding;

    public static GuestAccomodationPageFragment newInstance() {
        return new GuestAccomodationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccomodationPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);

        Button btnFilters = binding.btnFilters;
        Integer[] arraySpinner = new Integer[] {
                1, 2,3,4,5
        };
        Spinner numberOfGuestsSpinner = (Spinner) binding.guestNum;
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfGuestsSpinner.setAdapter(adapter);

        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
            View dialogView = getLayoutInflater().inflate(R.layout.filter_dialog, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });
        Button btnSearch = binding.btnAcceptFilters;
        btnSearch.setOnClickListener(v -> {

            int selectedNumberOfGuests = (int) numberOfGuestsSpinner.getSelectedItem();
            TextInputEditText startDateInput = binding.cicoInput;
            String startDate = startDateInput.getText().toString();
            TextInputEditText endDateInput =binding.cicoInput2;
            String endDate = endDateInput.getText().toString();
            TextView destination = binding.locationText;
            String location=destination.getText().toString();

            getDataFromClient("2024-01-01","2024-01-08",4,AccommodationType.HOTEL.name(),
                    0,0,null,
                    null,null,null,null);
        });



        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromClient(null,null,0,null,0,0,null,
                null,null,null,null);
    }
    @Override
    public void onResume() {
        super.onResume();
        getDataFromClient(null,null,0,null,0,0,null,
        null,null,null,null);
    }

    private void getDataFromClient(String begin, String end, int guestNumber, String type,
                                   double startPrice, double endPrice, String status,
                                   String country, String city, List<String> amenities, Integer hostId) {
        Call<ArrayList<Accomodation>> call = accommodationService.getAll(
                begin, end, guestNumber, type, startPrice, endPrice, status, country, city, amenities, hostId
        );

        call.enqueue(new Callback<ArrayList<Accomodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accomodation>> call, Response<ArrayList<Accomodation>> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Meesage recieved");
                    System.out.println(response.body());
                    products = response.body();
                    adapter = new AccomodationListAdapter(getActivity(), products);
                    //setListAdapter(adapter);
                    ListView listView=binding.list;
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("REZ", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Accomodation>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
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

//    private void prepareProductList(ArrayList<Accomodation> products){
//        products.add(new Accomodation(
//                1L,
//                "Accomodation name",
//                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
//                R.drawable.ap1));
//        products.add(new Accomodation(
//                2L,
//                "Accomodation name",
//                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
//                R.drawable.ap2));
//        products.add(new Accomodation(
//                3L,
//                "Accomodation name",
//                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
//                R.drawable.ap4));
//        products.add(new Accomodation(
//                4L,
//                "Accomodation name",
//                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
//                R.drawable.ap5));
//        products.add(new Accomodation(
//                5L,
//                "Accomodation name",
//                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
//                R.drawable.ap6));
//    }
}
