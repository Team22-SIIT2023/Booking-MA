package com.example.booking_team22.fragments.accomodation;

import static android.content.Context.MODE_PRIVATE;

import static com.example.booking_team22.clients.ClientUtils.accommodationService;
import static com.example.booking_team22.clients.ClientUtils.requestService;
import static com.example.booking_team22.clients.ClientUtils.userService;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_team22.R;
import com.example.booking_team22.activities.EditAccommodationFreeTimeSlots;
import com.example.booking_team22.adapters.AmenityListAdapter;
import com.example.booking_team22.adapters.CommentsAdapter;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.FragmentAccommodationDetailBinding;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Guest;
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
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailFragment extends Fragment {
    private Accomodation accommodation=new Accomodation();

    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;

    private ArrayList<Amenity> amenities = new ArrayList<Amenity>();
    private ArrayList<Comment> comments=new ArrayList<>();
    CommentsAdapter commentsAdapter;
    AmenityListAdapter adapter;
    private MapView mapView;
    private User user;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private GoogleMap googleMap;
    private SharedPreferences sp;
    private String userType;

    private Accomodation detailAccommodation;

    FragmentAccommodationDetailBinding binding;

    public AccommodationDetailFragment() {
        // Required empty public constructor
    }
    public static AccommodationDetailFragment newInstance() {
        return new AccommodationDetailFragment();
    }

    public static AccommodationDetailFragment newInstance(Accomodation detailAccommodation) {
        AccommodationDetailFragment fragment = new AccommodationDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("detailAccommodation", detailAccommodation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        detailAccommodation = args.getParcelable("detailAccommodation");
        if (detailAccommodation != null) {
            accommodation.setId(detailAccommodation.getId());
            accommodation.setName(detailAccommodation.getName());
            accommodation.setDescription(detailAccommodation.getDescription());
            accommodation.setHost(detailAccommodation.getHost());
            accommodation.setAmenities(detailAccommodation.getAmenities());
            accommodation.setFreeTimeSlots(detailAccommodation.getFreeTimeSlots());
            accommodation.setAddress(detailAccommodation.getAddress());
        }
    }
    private boolean enableListScroll(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return true;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        listView.measure(0, 0);
        params.height = listView.getMeasuredHeight() * listAdapter.getCount() + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        return false;
    }

    private void updateNumberText(int value) {
        //for later
    }
    private void setDate(TextInputEditText input) {
        input.setOnClickListener(v -> {
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

            Date dateMin = new GregorianCalendar(2024, Calendar.JANUARY, 3).getTime();
            Date dateMax = new GregorianCalendar(2024, Calendar.JANUARY, 26).getTime();

            datePickerDialog.getDatePicker().setMinDate(dateMin.getTime());
            datePickerDialog.getDatePicker().setMaxDate(dateMax.getTime());


            datePickerDialog.show();
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentAccommodationDetailBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

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



        TextView description=binding.textAccommodationDescription;
        description.setText(accommodation.getDescription());

        TextView name=binding.textAccommodationName;
        name.setText(accommodation.getName());

        RatingBar ratingBar=binding.rating;
        Call<Double> callRating = ClientUtils.commentService.getAccommodationRating(accommodation.getId());
        callRating.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful()) {
                    ratingBar.setRating(response.body().floatValue());
                }
            }
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
            }
        });

        TextView addressTxt=binding.addressFiled;
        addressTxt.setText(accommodation.getAddress().getCountry()+","
                +accommodation.getAddress().getCity()+", "+accommodation.getAddress().getAddress());

        TextView host=binding.txtHost;
        host.setText(accommodation.getHost().getFirstName()+" "+
                accommodation.getHost().getLastName()+"\n"+accommodation.getHost().getAccount().getUsername());


        adapter = new AmenityListAdapter(getActivity(), accommodation.getAmenities());
        System.out.println(accommodation.getAmenities());

        binding.amenityList.setAdapter(adapter);
        enableListScroll(binding.amenityList);


        TextInputEditText startDateInput = binding.cicoInput;
        TextInputEditText endDateInput =binding.cicoInput2;
        Spinner numberOfGuestsSpinner = binding.spinner;
        TextInputEditText priceInput = binding.priceInput;
        CalendarView calendarView=binding.calendarView;
        Date dateMin = new GregorianCalendar(2024, Calendar.JANUARY, 3).getTime();
        Date dateMax = new GregorianCalendar(2024, Calendar.JANUARY, 26).getTime();
        calendarView.setMinDate(dateMin.getTime());
        calendarView.setMaxDate(dateMax.getTime());
        startDateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        endDateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        numberOfGuestsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updatePrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });



        Call<ArrayList<Comment>> callComment = ClientUtils.commentService.getAccommodationComments(accommodation.getId(), Status.ACTIVE.name());
        callComment.enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    System.out.println(response.body());
                    comments = response.body();
                    commentsAdapter=new CommentsAdapter(getActivity(),comments);
                    binding.commentsList.setAdapter(commentsAdapter);
                    enableListScroll(binding.commentsList);
                    commentsAdapter.notifyDataSetChanged();
                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });

        LinearLayout linearLayout = binding.layoutPictures;
        Call<List<String>> callImages = ClientUtils.accommodationService.getImages(accommodation.getId());
        callImages.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> images = response.body();
                    if (images != null && !images.isEmpty()) {
                        for(String imageCode:images){
                            byte[] decodedBytes = Base64.decode(imageCode, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                            ImageView imageView = new ImageView(getActivity());
                            imageView.setImageBitmap(bitmap);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            layoutParams.width = 650; // Set your desired width in pixels
                            layoutParams.height = 650; // Set your desired height in pixels
                            layoutParams.leftMargin=10;
                            layoutParams.rightMargin=10;
                            imageView.setLayoutParams(layoutParams);
                            linearLayout.addView(imageView);
                        }

                    }
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
            }
        });

        Button editButton = binding.btnEditAccommodation;
        Button editPriceButton = binding.btnEditPriceAccommodation;
        Button addComment=binding.btnAddComments;
        LinearLayout reservationLayout=binding.resrvationLayout;
        if(!userType.equals("ROLE_HOST")){
            editButton.setVisibility(View.INVISIBLE);
            editPriceButton.setVisibility(View.INVISIBLE);
        }else{
            reservationLayout.setVisibility(View.GONE);
            addComment.setVisibility(View.GONE);
        }
        editButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit,0,0,0);
        editPriceButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar,0,0,0);

        editButton.setOnClickListener(v ->{
            LayoutInflater.from(getActivity());
            View customLayout= LayoutInflater.from(getContext()).inflate(R.layout.activity_edit_accomodation,null);
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setView(customLayout);
            AlertDialog dialog=builder.create();
            dialog.show();
        });

        editPriceButton.setOnClickListener(v ->{
//            Bundle args = new Bundle();
//            args.putParcelable("detailAccommodation", detailAccommodation);
//            Intent intent = new Intent(getContext(), EditAccommodationFreeTimeSlots.class);
//            intent.putExtras(args);
//
//            startActivity(intent);
//            LayoutInflater.from(getActivity());
//            View customLayout= LayoutInflater.from(getContext()).inflate(R.layout.activity_edit_accommodation_free_time_slots,null);
//            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//            builder.setView(customLayout);
//            AlertDialog dialog=builder.create();
//            dialog.show();
//            Bundle args = new Bundle();
//            args.putParcelable("detailAccommodation", detailAccommodation);
            Bundle args = new Bundle();
            NavController navController = Navigation.findNavController(getActivity(),R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_edit_price_and_timeslot, args);
        });


        Button myButton = binding.btnAddComments;
        myButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add,0,0,0);

        mapView=binding.mapView;
        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(googleMap1 -> {
//            LatLng markerLatLng=new LatLng(47.5189687,18.9606965);
//            googleMap1.addMarker(new MarkerOptions().position(markerLatLng).title("Marker title"));
//            googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,12));
//        });

        String street=accommodation.getAddress().getAddress();
        String city=accommodation.getAddress().getCity();
        String country=accommodation.getAddress().getCountry();
        String location=street+", "+city+", "+country;

        Geocoder geocoder = new Geocoder(requireContext());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(location, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                LatLng markerLatLng = new LatLng(latitude, longitude);

                mapView.getMapAsync(googleMap1 -> {
                    googleMap1.addMarker(new MarkerOptions().position(markerLatLng).title(location));
                    googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, 12));
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);

        Integer[] arraySpinner = new Integer[] {
                1, 2,3,4,5
        };
        Spinner s = (Spinner) binding.spinner;
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


        binding.reserveButton.setOnClickListener(v->{

            String startDate=startDateInput.getText().toString();
            String endDate=endDateInput.getText().toString();

            int numberOfGuests = (int) numberOfGuestsSpinner.getSelectedItem();
            double price = Double.parseDouble(priceInput.getText().toString());

            TimeSlot timeSlot = new TimeSlot(startDate,endDate);
            Guest guest = new Guest(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(),
                                user.getPhoneNumber(), user.getAccount(), user.getPicturePath(),
                                user.getLastPasswordResetDate(), user.getActivationLink(), user.getActivationLinkDate());
            RequestStatus status = RequestStatus.WAITING;
            ReservationRequest reservationRequest = new ReservationRequest(
                    timeSlot,
                    price,
                    guest,
                    numberOfGuests,
                    accommodation,
                    status
            );
            if(price!=0){makeReservationWithCalculatedPrice(reservationRequest);}
        });
        return root;
    }

    private void makeReservationWithCalculatedPrice(ReservationRequest reservationRequest) {
        Call<ReservationRequest> call = requestService.createRequest(reservationRequest);
        call.enqueue(new Callback<ReservationRequest>() {
            @Override
            public void onResponse(Call<ReservationRequest> call, Response<ReservationRequest> response) {
                if (response.isSuccessful()) {
                    ReservationRequest createdRequest = response.body();
                    Log.d("POST_SUCCESS", "Reservation request created: " + createdRequest);
                     Toast toastMessage=new Toast(requireContext());
                     toastMessage.setText("Reservation request sent!");
                     toastMessage.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 16);
                     toastMessage.setDuration(Toast.LENGTH_SHORT);
                     toastMessage.show();
                } else {
                    Log.d("POST_ERROR", "Error code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ReservationRequest> call, Throwable t) {
                Log.e("POST_FAILURE", "Error: " + t.getMessage(), t);
            }
        });

    }
    private void updatePrice() {

        TextInputEditText startDateInput = binding.cicoInput;
        TextInputEditText endDateInput =binding.cicoInput2;
        Spinner numberOfGuestsSpinner = binding.spinner;
        TextInputEditText priceInput = binding.priceInput;


        String startDate = startDateInput.getText().toString();
        String endDate = endDateInput.getText().toString();

        if ( !startDate.isEmpty() && !endDate.isEmpty() && numberOfGuestsSpinner.getSelectedItem() != null) {
            int numberOfGuests = (Integer) numberOfGuestsSpinner.getSelectedItem();

            Call<Double> callPrice = accommodationService.calculatePrice(accommodation.getId(), numberOfGuests, startDate, endDate);
            callPrice.enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if (response.isSuccessful()) {
                        Double calculatedPrice = response.body();
                        priceInput.setText(String.valueOf(calculatedPrice));
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

    private void prepareProductList(ArrayList<Amenity> amenities){
//        amenities.add(new Amenity(
//                1L,
//                "Wifi"
//                //R.drawable.ic_wifi
//        ));
//        amenities.add(new Amenity(
//                1L,
//                "Air conditioning"
//                //R.drawable.ic_air
//        ));
//        amenities.add(new Amenity(
//                1L,
//                "Room service"
//                //R.drawable.ic_room_service
//        ));
//        amenities.add(new Amenity(
//                1L,
//                "Pets"
//                //R.drawable.ic_pets
//        ));
//        amenities.add(new Amenity(
//                1L,
//                "Pool"
//                //R.drawable.ic_pool
//        ));
    }
    private void prepareCommentsList(ArrayList<Comment> comments){
//        comments.add(new Comment(
//                1L,
//                "a@gmail.com",
//                "Comment text....",
//                "12.12.2023."
//        ));
//        comments.add(new Comment(
//                2L,
//                "a@gmail.com",
//                "Comment text....",
//                "12.12.2023."
//        ));
//        comments.add(new Comment(
//                3L,
//                "a@gmail.com",
//                "Comment text....",
//                "12.12.2023."
//        ));
//        comments.add(new Comment(
//                4L,
//                "a@gmail.com",
//                "Comment text....",
//                "12.12.2023."
//        ));
    }
}
