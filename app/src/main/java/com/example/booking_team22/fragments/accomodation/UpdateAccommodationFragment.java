package com.example.booking_team22.fragments.accomodation;

import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.accommodationService;
import static com.example.booking_team22.clients.ClientUtils.amenityService;
import static com.example.booking_team22.clients.ClientUtils.requestService;
import static com.example.booking_team22.clients.ClientUtils.userService;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AmenityListAdapter;
import com.example.booking_team22.adapters.CommentsAdapter;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.FragmentAccommodationDetailBinding;
import com.example.booking_team22.databinding.FragmentUpdateAccommodationBinding;
import com.example.booking_team22.model.AccommodationStatus;
import com.example.booking_team22.model.AccommodationType;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Address;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Guest;
import com.example.booking_team22.model.Host;
import com.example.booking_team22.model.PricelistItem;
import com.example.booking_team22.model.RequestStatus;
import com.example.booking_team22.model.ReservationRequest;
import com.example.booking_team22.model.Status;
import com.example.booking_team22.model.TimeSlot;
import com.example.booking_team22.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAccommodationFragment extends Fragment {
    private Accomodation accommodation=new Accomodation();
    private ArrayList<Amenity> amenities = new ArrayList<Amenity>();
    AmenityListAdapter adapter;
    private  BottomSheetDialog bottomSheetDialog;

    private String accessToken;
    private User user;
    private SharedPreferences sp;
    private String userType;
    private Spinner accommodationType;
    AmenityListAdapter amenityListAdapter;

    private TextInputLayout accommodationName;

    private TextInputLayout accommodationCountry;

    private TextInputLayout accommodationCity;
    private TextInputLayout accommodationAddress;
    private TextInputLayout accommodationDescription;
    private TextInputLayout accommodationMinGuests;
    private TextInputLayout accommodationMaxGuests;
    private TextInputLayout reservationDeadline;
    private CheckBox automaticConfirmation;
    private CheckBox pricePerGuest;
    private Button saveChanges;

    FragmentUpdateAccommodationBinding binding;

    public UpdateAccommodationFragment() {
    }
    public static UpdateAccommodationFragment newInstance() {
        return new UpdateAccommodationFragment();
    }

    public static UpdateAccommodationFragment newInstance(Accomodation updateAccommodation) {
        UpdateAccommodationFragment fragment = new UpdateAccommodationFragment();
        Bundle args = new Bundle();
        args.putParcelable("updateAccommodation", updateAccommodation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        Accomodation updateAccommodation = args.getParcelable("updateAccommodation");
        if (updateAccommodation != null) {
            accommodation.setId(updateAccommodation.getId());
            accommodation.setName(updateAccommodation.getName());
            accommodation.setDescription(updateAccommodation.getDescription());
            accommodation.setHost(updateAccommodation.getHost());
            accommodation.setAmenities(updateAccommodation.getAmenities());
            accommodation.setFreeTimeSlots(updateAccommodation.getFreeTimeSlots());
            accommodation.setAddress(updateAccommodation.getAddress());
            accommodation.setMinGuests(updateAccommodation.getMinGuests());
            accommodation.setMaxGuests(updateAccommodation.getMaxGuests());
            accommodation.setPriceList(updateAccommodation.getPriceList());
            accommodation.setAutomaticConfirmation(updateAccommodation.isAutomaticConfirmation());
            accommodation.setPricePerGuest(updateAccommodation.isPricePerGuest());
            accommodation.setReservationDeadline(updateAccommodation.getReservationDeadline());
            accommodation.setStatus(updateAccommodation.getStatus());
            accommodation.setType(updateAccommodation.getType());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentUpdateAccommodationBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        sp= getActivity().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        userType=sp.getString("userType","");
        accessToken = sp.getString("accessToken", "");
        long id=sp.getLong("userId",0L);

        Call<User> callUser = userService.getUser(id); // Assuming you have a method in your UserApiClient to get a user by ID
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    user = response.body();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.filter_dialog, null);
        Call<ArrayList<Amenity>> callAmenity = amenityService.getAll("Bearer " + accessToken);
        callAmenity.enqueue(new Callback<ArrayList<Amenity>>() {
            @Override
            public void onResponse(Call<ArrayList<Amenity>> call, Response<ArrayList<Amenity>> response) {
                if (response.code() == 200) {
                    amenities = response.body();
                    amenityListAdapter = new AmenityListAdapter(true, getActivity(), amenities);

                    // Set checked amenities after adapter creation
                    amenityListAdapter.setCheckedAmenities(accommodation.getAmenities());
                    amenityListAdapter.notifyDataSetChanged();


                    Log.d("Checked Amenities", "Total Checked Amenities: " + amenityListAdapter.checkedAmenities.size());

                    ListView listAmenities = dialogView.findViewById(R.id.allAmenities);
                    listAmenities.setAdapter(amenityListAdapter);
                    bottomSheetDialog.setContentView(dialogView);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Amenity>> call, Throwable t) {
                // Handle failure
            }
        });

        Button btnFilters = binding.btnFiltersEdit;
        btnFilters.setOnClickListener(v -> {
            bottomSheetDialog.show();
        });

        accommodationType = root.findViewById(R.id.spinner);
        String[] arraySpinner = new String[]{"Apartment", "Villa", "Hotel", "Motel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accommodationType.setAdapter(adapter);
        String selectedType = accommodation.getType().toString().toLowerCase();
        int index = -1;
        for (int i = 0; i < arraySpinner.length; i++) {
            if (arraySpinner[i].toLowerCase().equals(selectedType)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            accommodationType.setSelection(index);
        }


        this.accommodationName = binding.accommodationName;
        this.accommodationCountry = binding.accommodationCountry;
        this.accommodationCity = binding.accommodationCity;
        this.accommodationAddress = binding.accommodationAddress;
        this.accommodationDescription = binding.accommodationDescription;
        this.accommodationMinGuests = binding.accommodationMinGuests;
        this.accommodationMaxGuests = binding.accommodationMaxGuests;
        this.reservationDeadline = binding.deadline;
        this.automaticConfirmation = binding.automaticConfirmation;
        this.pricePerGuest = binding.pricePerGuest;
        this.saveChanges = binding.buttonSave;
        this.accommodationName.getEditText().setText(accommodation.getName());
        this.accommodationCountry.getEditText().setText(accommodation.getAddress().getCountry());
        this.accommodationCity.getEditText().setText(accommodation.getAddress().getCity());
        this.accommodationAddress.getEditText().setText(accommodation.getAddress().getAddress());
        this.accommodationDescription.getEditText().setText(accommodation.getDescription());
        this.accommodationMinGuests.getEditText().setText(String.valueOf(accommodation.getMinGuests()));
        this.accommodationMaxGuests.getEditText().setText(String.valueOf(accommodation.getMaxGuests()));
        this.reservationDeadline.getEditText().setText(String.valueOf(accommodation.getReservationDeadline()));
        this.automaticConfirmation.setChecked(accommodation.isAutomaticConfirmation());
        this.pricePerGuest.setChecked(accommodation.isPricePerGuest());
        return root;
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Accomodation> call;
                updateAccommodation();
                call = ClientUtils.accommodationService.updateAccommodation("Bearer " + accessToken, accommodation, accommodation.getId());

                call.enqueue(new Callback<Accomodation>() {
                    @Override
                    public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                        if (response.code() == 201){
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
    private void updateAccommodation() {
        String name = accommodationName.getEditText().getText().toString();
        String country = accommodationCountry.getEditText().getText().toString();
        String city = accommodationCity.getEditText().getText().toString();
        String address = accommodationAddress.getEditText().getText().toString();
        String description = accommodationDescription.getEditText().getText().toString();
        int minGuests = Integer.parseInt(accommodationMinGuests.getEditText().getText().toString());
        int maxGuests = Integer.parseInt(accommodationMaxGuests.getEditText().getText().toString());
        int resDeadline = Integer.parseInt(reservationDeadline.getEditText().getText().toString());
        AccommodationType type = getAccommodationType(accommodationType.getSelectedItem().toString());

        if (name.length() == 0
                && country.length() == 0
                && city.length() == 0
                && address.length() == 0
                && description.length() == 0) {
            return;
        }
        accommodation.setType(type);
        accommodation.setName(name);
        accommodation.setDescription(description);
        accommodation.setMinGuests(minGuests);
        accommodation.setMaxGuests(maxGuests);
        accommodation.setPricePerGuest(pricePerGuest.isChecked());
        accommodation.setAutomaticConfirmation(automaticConfirmation.isChecked());
        accommodation.setReservationDeadline(resDeadline);
        Address oldAddress = accommodation.getAddress();
        oldAddress.setCountry(country);
        oldAddress.setCity(city);
        oldAddress.setAddress(address);
        accommodation.setAddress(oldAddress);
        Host host = new Host(user.getId(),user.getFirstName()
                ,user.getLastName(),user.getAddress()
                ,user.getPhoneNumber(),user.getAccount()
                ,"",user.getLastPasswordResetDate(),user.getActivationLink(),user.getActivationLinkDate());
        accommodation.setHost(host);
        accommodation.setAmenities(amenityListAdapter.checkedAmenities);
        accommodation.setStatus(AccommodationStatus.UPDATED);
    }

    public AccommodationType getAccommodationType(String type) {
        switch (type) {
            case "Apartment":
                return AccommodationType.APARTMENT;
            case "Hotel":
                return AccommodationType.HOTEL;
            case "Motel":
                return AccommodationType.MOTEL;
            case "Villa":
                return AccommodationType.VILLA;
        }
        return null;
    }



}
