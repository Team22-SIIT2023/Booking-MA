package com.example.booking_team22.fragments.accomodation;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.accommodationService;
import static com.example.booking_team22.clients.ClientUtils.amenityService;
import static com.example.booking_team22.clients.ClientUtils.userService;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.content.ContentResolver;
import android.widget.Toast;


import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AmenityListAdapter;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.FragmentCreateAccommodationBinding;
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

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccommodationFragment extends Fragment {

    private int mYear, mMonth, mDay;
    private ArrayList<Amenity> amenities = new ArrayList<>();
    private TextInputLayout accommodationName , accommodationAddress, accommodationCity, accommodationCountry, accommodationDescription;
    private TextInputLayout accommodationPrice, accommodationMinGuests, accommodationMaxGuests, reservationDeadline;
    private TextInputEditText cicoInput, cicoInput2;
    private CheckBox automaticConfirmation, pricePerGuest;
    private Button confirm;
    private Spinner accommodationType;
    private Accomodation newAccommodation;
    private  BottomSheetDialog bottomSheetDialog;
    private FragmentCreateAccommodationBinding binding;
    private SharedPreferences sp;
    private String userType;
    private ArrayList<Uri>imageUris=new ArrayList<>();
    private User user;
    private String accessToken;
    private int PICK_IMAGES_REQUEST=3;

    private String name, address, city, country, description, startDate, endDate;
    private double price;
    private int minGuests, maxGuests, resDeadline;
    AmenityListAdapter amenityListAdapter;
//    ArrayList<Amenity>amenities=new ArrayList<>();

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

        sp = getActivity().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");

        bottomSheetDialog = new BottomSheetDialog(getActivity());

        View dialogView = getLayoutInflater().inflate(R.layout.filter_dialog, null);


        Call<ArrayList<Amenity>> callAmenity = amenityService.getAll("Bearer "+accessToken);
        callAmenity.enqueue(new Callback<ArrayList<Amenity>>() {
            @Override
            public void onResponse(Call<ArrayList<Amenity>> call, Response<ArrayList<Amenity>> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    System.out.println(response.body());
                    amenities = response.body();
                    amenityListAdapter = new AmenityListAdapter(true,getActivity(), amenities);

                    ListView listAmenities=dialogView.findViewById(R.id.allAmenities);
                    listAmenities.setAdapter(amenityListAdapter);
                    bottomSheetDialog.setContentView(dialogView);

                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Amenity>> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });

        Button btnFilters = binding.btnFiltersEdit;
        btnFilters.setOnClickListener(v -> {
            bottomSheetDialog.show();
        });

        Button btnUploadImages=binding.buttonAddPhotos;
        btnUploadImages.setOnClickListener(v -> {
            chooseImages();
        });



        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);

        return root;
    }

    private void chooseImages() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST);
    }
    private void uploadImages(Long id) {
        MultipartBody.Part[] parts = new MultipartBody.Part[imageUris.size()];

        for (int i = 0; i < imageUris.size(); i++) {
            Uri uri = imageUris.get(i);
            File file = new File(getRealPathFromUri(uri)); // Implement getRealPathFromUri
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            parts[i] = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
        }

        Call<Accomodation> call = accommodationService.uploadImages("Bearer "+accessToken,parts,id);

        call.enqueue(new Callback<Accomodation>() {
            @Override
            public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                if (response.isSuccessful()) {
                    Log.d("REZ","Dobro"+response.body());
                    // Handle successful response
                    //Toast.makeText(YourActivity.this, "Images uploaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("REZ","response error"+response.errorBody());

                    // Handle error response
                    //Toast.makeText(YourActivity.this, "Failed to upload images", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Accomodation> call, Throwable t) {
                // Handle failure
                //Toast.makeText(YourActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return uri.getPath();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == RESULT_OK) {
            if (data != null) {
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    // Multiple images selected
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri uri = clipData.getItemAt(i).getUri();
                        imageUris.add(uri);
                    }
                } else {
                    Uri uri = data.getData();
                    imageUris.add(uri);

                }
            }
        }
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
//        mViewModel = new ViewModelProvider(this).get(NewProductViewModel.class);

        this.accommodationName = binding.accommodationName;
        this.accommodationAddress = binding.accommodationAddress;
        this.accommodationCity = binding.accommodationCity;
        this.accommodationCountry = binding.accommodationCountry;
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

        accommodationType = (Spinner) binding.accommodationType;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accommodationType.setAdapter(adapter);

        sp = getActivity().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        userType = sp.getString("userType","");
        long id = sp.getLong("userId",0L);

        Call<User> callUser = userService.getUser(id);
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

                if (addNewProduct()) {
                    Call<Accomodation> call = ClientUtils.accommodationService.createAccommodation("Bearer " + accessToken,newAccommodation);

                    call.enqueue(new Callback<Accomodation>() {
                        @Override
                        public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                            if (response.code() == 201){
                                Log.d("OKKK","Meesage recieved");
                                Accomodation product1 = response.body();
                                uploadImages(product1.getId());
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
            }
        });
    }

    private boolean addNewProduct() {
        name = accommodationName.getEditText().getText().toString();
        address = accommodationAddress.getEditText().getText().toString();
        city = accommodationCity.getEditText().getText().toString();
        country = accommodationCountry.getEditText().getText().toString();
        description = accommodationDescription.getEditText().getText().toString();
        startDate = cicoInput.getText().toString();
        endDate = cicoInput2.getText().toString();
        AccommodationType type = getAccommodationType(accommodationType.getSelectedItem().toString());

        if (!checkAllFields()) {
            return false;
        }

        price = Double.parseDouble(accommodationPrice.getEditText().getText().toString());
        minGuests = Integer.parseInt(accommodationMinGuests.getEditText().getText().toString());
        maxGuests = Integer.parseInt(accommodationMaxGuests.getEditText().getText().toString());
        resDeadline = Integer.parseInt(reservationDeadline.getEditText().getText().toString());

        if (price < 0 || minGuests < 0 || maxGuests < 0 || resDeadline < 0) {
            Toast.makeText(getActivity(),"Incorrect input!", Toast.LENGTH_SHORT).show();
            return false;
        }


        newAccommodation = new Accomodation();
        newAccommodation.setName(name);
        newAccommodation.setDescription(description);
        newAccommodation.setMinGuests(minGuests);
        newAccommodation.setMaxGuests(maxGuests);
        newAccommodation.setPricePerGuest(pricePerGuest.isChecked());
        newAccommodation.setAutomaticConfirmation(automaticConfirmation.isChecked());
        newAccommodation.setReservationDeadline(resDeadline);

        TimeSlot timeslot = new TimeSlot(startDate, endDate);
        ArrayList<TimeSlot> timeslots = new ArrayList<>();
        timeslots.add(timeslot);

        newAccommodation.setAmenities(amenityListAdapter.checkedAmenities);

        PricelistItem pricelistItem = new PricelistItem();
        pricelistItem.setPrice(price);
        pricelistItem.setTimeSlot(timeslot);
        ArrayList<PricelistItem> pricelist = new ArrayList<>();
        pricelist.add(pricelistItem);

        Address accAddress = new Address();
        accAddress.setAddress(address);
        accAddress.setCity(city);
        accAddress.setCountry(country);

        Host host = new Host(user);

        newAccommodation.setHost(host);
        newAccommodation.setAddress(accAddress);
        newAccommodation.setType(type);
        newAccommodation.setFreeTimeSlots(timeslots);
        newAccommodation.setPriceList(pricelist);

        return true;
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

    public boolean checkAllFields() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (name.length() == 0) {
            this.accommodationName.setError("This field is required!");
            return false;
        }
        if (address.length() == 0) {
            this.accommodationAddress.setError("This field is required!");
            return false;
        }
        if (city.length() == 0) {
            this.accommodationCity.setError("This field is required!");
            return false;
        }
        if (country.length() == 0) {
            this.accommodationCountry.setError("This field is required!");
            return false;
        }
        if (description.length() == 0) {
            this.accommodationDescription.setError("This field is required!");
            return false;
        }
        if (accommodationMaxGuests.getEditText().getText().toString().length()==0) {
            this.accommodationMaxGuests.setError("This field is required!");
            return false;
        }
        if (accommodationMinGuests.getEditText().getText().toString().length()==0) {
            this.accommodationMinGuests.setError("This field is required!");
            return false;
        }
        if (accommodationPrice.getEditText().getText().toString().length()==0) {
            this.accommodationPrice.setError("This field is required!");
            return false;
        }
        if (reservationDeadline.getEditText().getText().toString().length()==0) {
            this.reservationDeadline.setError("This field is required!");
            return false;
        }
        if (Integer.parseInt(accommodationMaxGuests.getEditText().getText().toString()) < Integer.parseInt(accommodationMinGuests.getEditText().getText().toString())) {
            this.accommodationMaxGuests.setError("Max guests number must be greater!");
            return false;
        }
        if (LocalDate.parse(cicoInput.getText().toString(),formatter).isAfter(LocalDate.parse(cicoInput2.getText().toString(),formatter))) {
            this.cicoInput2.setError("End date must be after start date!");
            return false;
        }
        return true;
    }
}