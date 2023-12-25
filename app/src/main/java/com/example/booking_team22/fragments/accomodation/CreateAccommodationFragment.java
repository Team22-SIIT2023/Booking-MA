package com.example.booking_team22.fragments.accomodation;

import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.userService;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.booking_team22.R;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.ActivityEditAccomodationBinding;
import com.example.booking_team22.databinding.FragmentCreateAccommodationBinding;
import com.example.booking_team22.model.AccommodationStatus;
import com.example.booking_team22.model.AccommodationType;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Address;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.Host;
import com.example.booking_team22.model.PricelistItem;
import com.example.booking_team22.model.TimeSlot;
import com.example.booking_team22.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccommodationFragment extends Fragment {

    private int mYear, mMonth, mDay;

    private TextInputLayout accommodationName;
    private TextInputLayout accommodationAddress;
    private TextInputLayout accommodationDescription;
    private TextInputLayout accommodationPrice;
    private TextInputLayout accommodationMinGuests;
    private TextInputLayout accommodationMaxGuests;
    private TextInputLayout reservationDeadline;
    private TextInputEditText cicoInput;
    private TextInputEditText cicoInput2;
    private CheckBox automaticConfirmation;
    private CheckBox pricePerGuest;
    private Button confirm;
    private Accomodation newAccommodation;
    FragmentCreateAccommodationBinding binding;

    private SharedPreferences sp;
    private String userType;
    private User user;


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
                            String formattedDate = String.format(Locale.US, "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                            input.setText(formattedDate);
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

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(NewProductViewModel.class);

        this.accommodationName = binding.accommodationName;
        this.accommodationAddress = binding.accommodationAddress;
        this.accommodationDescription = binding.accommodationDescription;
        this.accommodationPrice = binding.accommodationPrice;
        this.accommodationMinGuests = binding.accommodationMinGuests;
        this.accommodationMaxGuests = binding.accommodationMaxGuests;
        this.reservationDeadline = binding.deadline;
        this.cicoInput = binding.cicoInput;
        this.cicoInput2 = binding.cicoInput2;
        this.automaticConfirmation = binding.automaticConfirmation;
        this.pricePerGuest = binding.pricePerGuest;
        this.confirm = binding.buttonSave;

        String[] arraySpinner = new String[]{
                "Apartment", "Villa", "Hotel", "Motel"
        };

        Spinner s = (Spinner) binding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        sp= getActivity().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        userType=sp.getString("userType","");
        long id=sp.getLong("userId",0L);

        Call<User> callUser = userService.getUser(sp.getLong("userId",0L)); // Assuming you have a method in your UserApiClient to get a user by ID
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Log.d("USER", "Message received");
                    user = response.body();
                } else {
                    Log.d("USER_REQUEST", "Message received: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("USER_REQUEST", "Error: " + t.getMessage(), t);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Accomodation> call;

                Log.d("ShopApp", "Add product call");
                addNewProduct();
                call = ClientUtils.accommodationService.createAccommodation(newAccommodation);

                call.enqueue(new Callback<Accomodation>() {
                    @Override
                    public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                        if (response.code() == 201){
                            Log.d("REZ","Meesage recieved");
                            Log.d("VANJAAAA","Meesage recieved");
                            System.out.println(response.body());
                            Accomodation product1 = response.body();
                            System.out.println(product1);
                            getActivity().getSupportFragmentManager().popBackStack();
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Accomodation> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });
    }

    private void addNewProduct() {
        String name = accommodationName.getEditText().getText().toString();
        String address = accommodationAddress.getEditText().getText().toString();
        String description = accommodationDescription.getEditText().getText().toString();
        double price = Double.parseDouble(accommodationPrice.getEditText().getText().toString());
        int minGuests = Integer.parseInt(accommodationMinGuests.getEditText().getText().toString());
        int maxGuests = Integer.parseInt(accommodationMaxGuests.getEditText().getText().toString());
        int resDeadline = Integer.parseInt(reservationDeadline.getEditText().getText().toString());
        String startDate = cicoInput.getText().toString();
        String endDate = cicoInput2.getText().toString();

        if (name.length() == 0 && address.length() == 0 && description.length() == 0) {
            return;
        }

        newAccommodation = new Accomodation();
        newAccommodation.setName(name);
        newAccommodation.setDescription(description);
        newAccommodation.setMinGuests(minGuests);
        newAccommodation.setMaxGuests(maxGuests);
        newAccommodation.setPricePerGuest(pricePerGuest.isChecked());
        newAccommodation.setAutomaticConfirmation(automaticConfirmation.isChecked());
        newAccommodation.setReservationDeadline(resDeadline);

//        LocalDate startDateLocal = LocalDate.parse(startDate);
//        LocalDate endDateLocal = LocalDate.parse(endDate);
        TimeSlot timeslot = new TimeSlot(startDate, endDate);
        ArrayList<TimeSlot> timeslots = new ArrayList<>();
        timeslots.add(timeslot);
        System.out.println("Timeslotssssssss");
        System.out.println(startDate);
        System.out.println(endDate);

        Amenity amenity = new Amenity();
        amenity.setId(2L);
        amenity.setAmenityName("pool");
        ArrayList<Amenity> amenities = new ArrayList<>();
        amenities.add(amenity);

        PricelistItem pricelistItem = new PricelistItem();
        pricelistItem.setPrice(price);
        pricelistItem.setTimeSlot(timeslot);
        ArrayList<PricelistItem> pricelist = new ArrayList<>();
        pricelist.add(pricelistItem);

        Address accAddress = new Address();
        Host host = new Host(user);

        newAccommodation.setHost(host);
        newAccommodation.setAddress(accAddress);
        newAccommodation.setType(AccommodationType.HOTEL);
//        newAccommodation.setStatus(AccommodationStatus.ACCEPTED);
        newAccommodation.setFreeTimeSlots(timeslots);
        newAccommodation.setPriceList(pricelist);
        newAccommodation.setAmenities(amenities);
    }
}