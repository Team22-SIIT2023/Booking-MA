package com.example.booking_team22.fragments.accomodation;

import static com.example.booking_team22.clients.ClientUtils.accommodationService;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.AccomodationCardBinding;
import com.example.booking_team22.databinding.FragmentAccomodationPageBinding;
import com.example.booking_team22.model.AccommodationType;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Amenity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestAccomodationPageFragment extends ListFragment {
    private int mYear, mMonth, mDay, mHour, mMinute;

    public static ArrayList<Accomodation> products = new ArrayList<Accomodation>();
    AccomodationListAdapter adapter;
    private String startDate=null;
    private String endDate=null;
    private String location=null;
    private String type=null;
    private List<String>amenities=new ArrayList<>();
    private int numberOfGuests=0;
    private int minPrice=0;
    private int maxPrice=0;
    private final Map<CheckBox, Boolean> checkboxStates = new HashMap<>();
    private Map<Long, Double> pricesMap=new HashMap<>();
    private Map<Long, Double> unitPricesMap=new HashMap<>();

    private  BottomSheetDialog bottomSheetDialog;
    private FragmentAccomodationPageBinding binding;
    private SharedPreferences sp;
    private String accessToken;

    public static GuestAccomodationPageFragment newInstance() {
        return new GuestAccomodationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccomodationPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sp = getActivity().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);

        Button btnFilters = binding.btnFilters;
        bottomSheetDialog = new BottomSheetDialog(getActivity());

        String[] arraySpinner = new String[] {
                "","HOTEL", "MOTEL", "VILLA", "APARTMENT"
        };
        Spinner typeSpinner = (Spinner) binding.typeSpinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);


        SeekBar minSeekBar = binding.minSeekBar;
        TextView minValueText = binding.minValueText;
        minSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView with the current min value
                minValueText.setText("Min: $" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }
        });
        SeekBar maxSeekBar = binding.maxSeekBar;
        TextView maxValueText =binding.maxValueText;
        maxSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView with the current max value
                maxValueText.setText("Max: $" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }
        });


        View dialogView = getLayoutInflater().inflate(R.layout.filter_dialog, null);
        bottomSheetDialog.setContentView(dialogView);
        btnFilters.setOnClickListener(v -> {
            Log.i("ShopApp", "Bottom Sheet Dialog");
            bottomSheetDialog.show();
        });

        Button btnSearch = binding.btnAcceptFilters;
        btnSearch.setOnClickListener(v -> {
            amenities.clear();

            minPrice = minSeekBar.getProgress();
            maxPrice = maxSeekBar.getProgress();

            LinearLayout checkboxContainer = bottomSheetDialog.findViewById(R.id.filterLayout);
            for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
                View childView = checkboxContainer.getChildAt(i);

                if (childView instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) childView;

                    if (checkBox.isChecked()) {
                        String optionText = checkBox.getText().toString();
                        amenities.add(optionText);
                    }
                }
            }


            EditText guestNum = binding.numberOfGuests;
            if(!guestNum.getText().toString().equals("")){
                numberOfGuests=Integer.parseInt(guestNum.getText().toString());
            }
            type = typeSpinner.getSelectedItem().toString();
            TextInputEditText startDateInput = binding.cicoInput;
            startDate = startDateInput.getText().toString();
            TextInputEditText endDateInput =binding.cicoInput2;
            endDate = endDateInput.getText().toString();
            EditText destination = binding.locationText;
            if(!destination.getText().toString().equals("")){
                location=destination.getText().toString();
            }
            if ( !startDate.isEmpty() && !endDate.isEmpty() && numberOfGuests != 0) {
                long numberOfNights = ChronoUnit.DAYS.between(LocalDate.parse(startDate), LocalDate.parse(endDate));
                for(Accomodation accomodation:products){
                    Call<Double> callPrice = accommodationService.calculatePrice("Bearer " + accessToken, accomodation.getId(), numberOfGuests, startDate, endDate);
                    callPrice.enqueue(new Callback<Double>() {
                        @Override
                        public void onResponse(Call<Double> call, Response<Double> response) {
                            if (response.isSuccessful()) {
                                Double calculatedPrice = response.body();
                                pricesMap.put(accomodation.getId(),calculatedPrice);
                                if(accomodation.isPricePerGuest()){
                                    unitPricesMap.put(accomodation.getId(),calculatedPrice/numberOfNights/numberOfGuests);
                                }else{
                                    unitPricesMap.put(accomodation.getId(),calculatedPrice/numberOfNights);
                                }

                            } else {
                            }
                        }
                        @Override
                        public void onFailure(Call<Double> call, Throwable t) {
                            // Handle network error or API call failure
                            t.printStackTrace();
                        }
                    });
                }
            }
            getDataFromClient(startDate,endDate,numberOfGuests,type,
                    minPrice,maxPrice,null,
                    location,null,amenities,null);
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
        binding.cicoInput.setText("");
        binding.cicoInput2.setText("");
        binding.numberOfGuests.setText("");
        binding.locationText.setText("");
        binding.typeSpinner.setSelection(0);
        pricesMap.clear();
        unitPricesMap.clear();
        getDataFromClient(null,null,0,null,0,0,null,
        null,null,null,null);
    }

    private void getDataFromClient(String begin, String end, int guestNumber, String type,
                                   double startPrice, double endPrice, String status,
                                   String country, String city, List<String> amenities, Integer hostId) {
        Call<ArrayList<Accomodation>> call = accommodationService.getAll("Bearer " + accessToken,
                begin, end, guestNumber, type, startPrice, endPrice, status, country, city, amenities, hostId
        );

        call.enqueue(new Callback<ArrayList<Accomodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accomodation>> call, Response<ArrayList<Accomodation>> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Meesage recieved");
                    System.out.println(response.body());
                    products = response.body();
                    for(Accomodation accomodation:products){
                        try {
                            accomodation.setPrice(pricesMap.get(accomodation.getId()));
                            accomodation.setUnitPrice(unitPricesMap.get(accomodation.getId()));
                        }catch (Exception ex){
                            accomodation.setPrice(0.0);
                            accomodation.setUnitPrice(0.0);
                        }
                    }
                    adapter = new AccomodationListAdapter(getActivity(), products);
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

                            String formattedDate = String.format(Locale.US, "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                            input.setText(formattedDate);
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
