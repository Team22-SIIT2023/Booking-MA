package com.example.booking_team22.fragments.accomodation;

import static com.example.booking_team22.clients.ClientUtils.accommodationService;
import static com.example.booking_team22.clients.ClientUtils.notificationService;
import static com.example.booking_team22.clients.ClientUtils.requestService;
import static com.example.booking_team22.clients.ClientUtils.userService;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.text.Editable;
import android.text.InputType;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AmenityListAdapter;
import com.example.booking_team22.adapters.CommentsAdapter;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.FragmentAccommodationDetailBinding;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Guest;
import com.example.booking_team22.model.HostNotificationSettings;
import com.example.booking_team22.model.Notification;
import com.example.booking_team22.model.NotificationType;
import com.example.booking_team22.model.RequestStatus;
import com.example.booking_team22.model.ReservationRequest;
import com.example.booking_team22.model.Status;
import com.example.booking_team22.model.TimeSlot;
import com.example.booking_team22.model.User;
import com.google.android.gms.maps.MapView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailFragment extends Fragment {
    float accommodationRating = 0;
    float hostRating = 0;
    private Button reportHostButton, viewHostCommentsButton;
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<Amenity> amenities = new ArrayList<Amenity>();
    private ArrayList<Comment> comments=new ArrayList<>();
    private CommentsAdapter commentsAdapter;
    private AmenityListAdapter adapter;
    private MapView mapView;
    private User user;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private SharedPreferences sp;
    private String userType, accessToken;
    private RatingBar ratingBarHost, ratingBarAccommodation;
    private Accomodation detailAccommodation;
    private FragmentAccommodationDetailBinding binding;
    private Guest guest;
    private String reportText;

    List<Calendar> disabledDates = new ArrayList<>();
    private HostNotificationSettings hostSettings;

    public AccommodationDetailFragment() { // Required empty public constructor
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
        sp = getActivity().getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");
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

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

            datePickerDialog.show();
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        binding = FragmentAccommodationDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sp = getActivity().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");

        userType = sp.getString("userType","");
        long id = sp.getLong("userId",0L);


        String street=detailAccommodation.getAddress().getAddress();
        String city=detailAccommodation.getAddress().getCity();
        String country=detailAccommodation.getAddress().getCountry();
        String location=street+", "+city+", "+country;

//        mapView=binding.mapView;
//        mapView.onCreate(savedInstanceState);
//        Geocoder geocoder = new Geocoder(requireContext());
//        List<Address> addresses = null;
//        try {
//            addresses = geocoder.getFromLocationName(location, 1);
//            if (addresses != null && addresses.size() > 0) {
//                Address address = addresses.get(0);
//                double latitude = address.getLatitude();
//                double longitude = address.getLongitude();
//                LatLng markerLatLng = new LatLng(latitude, longitude);
//
//                mapView.getMapAsync(googleMap1 -> {
//                    googleMap1.addMarker(new MarkerOptions().position(markerLatLng).title(location));
//                    googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, 12));
//                });
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }



        getSettings();

        Call<User> callUser = userService.getUser(id); // Assuming you have a method in your UserApiClient to get a user by ID
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Log.d("USER", "Message received");
                    user = response.body();
                    Log.d("USEERR",user.getFirstName());
                } else {
                    Log.d("USER_REQUEST", "Message received: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("USER_REQUEST", "Error: " + t.getMessage(), t);
            }
        });


        RadioButton rbtAutomatic=binding.automaticRbt;
        RadioButton rbtManual=binding.manualRbt;
        if(detailAccommodation.isAutomaticConfirmation()){
            rbtAutomatic.setChecked(true);
        }else{
            rbtManual.setChecked(true);
        }

        TextView description=binding.textAccommodationDescription;
        description.setText(detailAccommodation.getDescription());

        TextView name=binding.textAccommodationName;
        name.setText(detailAccommodation.getName());

        RatingBar ratingBar=binding.rating;
        Call<Double> callRating = ClientUtils.commentService.getAccommodationRating("Bearer " + accessToken, detailAccommodation.getId());
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
        String addressAcc = detailAccommodation.getAddress().getCountry()+","
                +detailAccommodation.getAddress().getCity()+", "+detailAccommodation.getAddress().getAddress();
        addressTxt.setText(addressAcc);

        TextView host=binding.txtHost;
        String hostTxt = detailAccommodation.getHost().getFirstName()+" "+
                detailAccommodation.getHost().getLastName()+"\n"+detailAccommodation.getHost().getAccount().getUsername();
        host.setText(hostTxt);

        reportHostButton = binding.reportHostButton;
        reportHostButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_report_user,0,0,0);

        reportHostButton.setOnClickListener(v ->{
//            reportHost();
            showReportDialog();

        });




        viewHostCommentsButton = binding.viewHostComments;
        viewHostCommentsButton.setOnClickListener(v ->{
            showHostComments();
        });


        adapter = new AmenityListAdapter(false,getActivity(), detailAccommodation.getAmenities());
        System.out.println(detailAccommodation.getAmenities());

        binding.amenityList.setAdapter(adapter);
        enableListScroll(binding.amenityList);


        TextInputEditText startDateInput = binding.cicoInput;
        TextInputEditText endDateInput =binding.cicoInput2;
        Spinner numberOfGuestsSpinner = binding.spinner;
        TextInputEditText priceInput = binding.priceInput;
        CalendarView calendarView=(CalendarView) binding.calendarView;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        for(TimeSlot timeSlot:detailAccommodation.getFreeTimeSlots()){
            try {
                Date startDate = formatter.parse(timeSlot.getStartDate());
                Date endDate = formatter.parse(timeSlot.getEndDate());

                Calendar calendarStartDate = Calendar.getInstance();
                calendarStartDate.setTime(startDate);
                Calendar calendarEndDate = Calendar.getInstance();
                calendarEndDate.setTime(endDate);

                while (!calendarStartDate.after(calendarEndDate)) {
                    if (calendarStartDate.after(Calendar.getInstance())){
                        disabledDates.add((Calendar) calendarStartDate.clone());
                    }
                    calendarStartDate.add(Calendar.DAY_OF_MONTH, 1);
                }

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
        calendarView.setDisabledDays(disabledDates);
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



        Call<ArrayList<Comment>> callComment = ClientUtils.commentService.getAccommodationComments("Bearer " + accessToken, detailAccommodation.getId(), Status.ACTIVE.name());
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
        Call<List<String>> callImages = ClientUtils.accommodationService.getImages("Bearer " + accessToken, detailAccommodation.getId());

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

        LinearLayout approvalLayout=binding.approvalLayout;

        if(!userType.equals("ROLE_HOST")){
            approvalLayout.setVisibility(View.GONE);
            editButton.setVisibility(View.INVISIBLE);
            editPriceButton.setVisibility(View.INVISIBLE);
        }else{
            reservationLayout.setVisibility(View.GONE);
            addComment.setVisibility(View.GONE);
            Button approvalBtn=binding.changeApprovalBtn;
            approvalBtn.setOnClickListener(v -> {
                detailAccommodation.setAutomaticConfirmation(rbtAutomatic.isChecked());
                updateApprovalType(detailAccommodation);

            });
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
            Bundle args = new Bundle();
            args.putParcelable("accommodation", detailAccommodation);
            NavController navController = Navigation.findNavController(getActivity(),R.id.fragment_nav_content_main);
            navController.navigate(R.id.nav_edit_price_and_timeslot, args);
        });


        Button myButton = binding.btnAddComments;
        myButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add,0,0,0);

        ratingBarHost = binding.ratingForHost;
        ratingBarHost.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratingInt = (int) rating;
                hostRating = ratingBar.getRating();
            }
        });

        binding.commentHostButton.setOnClickListener(v->{
            createHostComm();
        });

        ratingBarAccommodation = binding.ratingAcoommodation;

        ratingBarAccommodation.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratingInt = (int) rating;
                accommodationRating = ratingBar.getRating();
                Toast.makeText(getActivity(),"Rating" + ratingInt, Toast.LENGTH_SHORT).show();
            }
        });

        binding.commentAccommmodationButton.setOnClickListener(v->{
            createAccommodationComm();
        });



        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);

        Integer[] arraySpinner = new Integer[detailAccommodation.getMaxGuests() - detailAccommodation.getMinGuests() + 1];

        for (int i = detailAccommodation.getMinGuests(); i <= detailAccommodation.getMaxGuests(); i++) {
            arraySpinner[i - detailAccommodation.getMinGuests()] = i;
        }

        Spinner s = (Spinner) binding.spinner;
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


        binding.reserveButton.setOnClickListener(v->{

            String startDate=startDateInput.getText().toString();
            String endDate=endDateInput.getText().toString();
            RequestStatus status;
            int numberOfGuests = (int) numberOfGuestsSpinner.getSelectedItem();
            double price = Double.parseDouble(priceInput.getText().toString());

            TimeSlot timeSlot = new TimeSlot(startDate,endDate);
            this.guest = new Guest(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(),
                                user.getPhoneNumber(), user.getAccount(), user.getPicturePath(),
                                user.getLastPasswordResetDate(), user.getActivationLink(), user.getActivationLinkDate());
            if(detailAccommodation.isAutomaticConfirmation()){
                status = RequestStatus.ACCEPTED;
            }
            else{
                status = RequestStatus.PENDING;
            }
            ReservationRequest reservationRequest = new ReservationRequest(
                    timeSlot,
                    price,
                    guest,
                    numberOfGuests,
                    detailAccommodation,
                    status
            );
            if(price!=0){makeReservationWithCalculatedPrice(reservationRequest);}
        });

        return root;
    }

    private boolean isDateAllowed(Calendar selectedDate) {
        for (Calendar disabledDate : disabledDates) {
            if (selectedDate.equals(disabledDate)) {
                return true;
            }
        }
        return false;
    }


//     @Override
//     public void onResume() {
//         super.onResume();
//         mapView.onResume();
//     }

//     @Override
//     public void onPause() {
//         super.onPause();
//         mapView.onPause();
//     }

//     @Override
//     public void onDestroy() {
//         super.onDestroy();
//         mapView.onDestroy();
//     }

    private void updateApprovalType(Accomodation accommodation) {
        Call<Accomodation> call = accommodationService.updateAccommodationRequestApproval("Bearer "+accessToken,accommodation);
        call.enqueue(new Callback<Accomodation>() {
            @Override
            public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                if (response.isSuccessful()) {
                    Accomodation updatedAccommodation = response.body();
                    Toast toastMessage=new Toast(requireContext());
                    toastMessage.setText("Request approval type changed!");
                    toastMessage.setDuration(Toast.LENGTH_SHORT);
                    toastMessage.show();
                } else {
                    Log.d("Failed to update approval", "Error code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Accomodation> call, Throwable t) {
                Log.e("Failed to update approval", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void makeReservationWithCalculatedPrice(ReservationRequest reservationRequest) {
        Call<ReservationRequest> call = requestService.createRequest("Bearer "+accessToken,reservationRequest);
        call.enqueue(new Callback<ReservationRequest>() {
            @Override
            public void onResponse(Call<ReservationRequest> call, Response<ReservationRequest> response) {
                if (response.isSuccessful()) {
                    ReservationRequest createdRequest = response.body();
                    Log.d("POST_SUCCESS", "Reservation request created: " + createdRequest);
                    if(reservationRequest.getAccommodation().isAutomaticConfirmation()){
                        Call<Accomodation> callTimeSlots = accommodationService.changeFreeTimeSlots("Bearer "+accessToken,reservationRequest.getAccommodation().getId(), reservationRequest.getTimeSlot());

                        callTimeSlots.enqueue(new Callback<Accomodation>() {
                            @Override
                            public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                                if (response.isSuccessful()) {
                                    Accomodation accommodationDTO = response.body();
                                    Toast toastMessage=new Toast(requireContext());
                                    toastMessage.setText("Reservation accepted immediately!");
                                    toastMessage.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 16);
                                    toastMessage.setDuration(Toast.LENGTH_SHORT);
                                    toastMessage.show();
                                } else {
                                }
                            }

                            @Override
                            public void onFailure(Call<Accomodation> call, Throwable t) {
                            }
                        });
                    }
                    else{
                        Toast toastMessage=new Toast(requireContext());
                        toastMessage.setText("Reservation request sent!");
                        toastMessage.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 16);
                        toastMessage.setDuration(Toast.LENGTH_SHORT);
                        toastMessage.show();
                    }
                    String text="User "+createdRequest.getGuest().getAccount().getUsername()+" has made a reservation request for " + createdRequest.getAccommodation().getName();
                    if(checkNotificationStatus(NotificationType.RESERVATION_REQUEST)){
                        createNotification(text, NotificationType.RESERVATION_REQUEST);
                    }

                } else {
                    if(response.code()==400){
                        Toast toastMessage=new Toast(requireContext());
                        toastMessage.setText("Request cannot be sent!");
                        toastMessage.setDuration(Toast.LENGTH_SHORT);
                        toastMessage.show();
                    }
                    Log.d("POST_ERROR", "Error code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ReservationRequest> call, Throwable t) {
                Log.e("POST_FAILURE", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void createNotification(String text, NotificationType type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        Notification notification=new Notification(detailAccommodation.getHost(),text,formattedDate,type);
        Call<Notification> call = notificationService.createUserNotification("Bearer "+accessToken,notification);
        call.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    System.out.println(response.body());

                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
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

            Call<Double> callPrice = accommodationService.calculatePrice("Bearer " + accessToken, detailAccommodation.getId(), numberOfGuests, startDate, endDate);

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

    public void createAccommodationComm() {
        TextInputLayout hostComm = binding.inputAccommodationComment;

        this.guest = new Guest(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(),
                user.getPhoneNumber(), user.getAccount(), user.getPicturePath(),
                user.getLastPasswordResetDate(), user.getActivationLink(), user.getActivationLinkDate());

        Comment accommodationComment = new Comment();
        accommodationComment.setDate(LocalDate.now().toString());
        accommodationComment.setGuest(this.guest);
        accommodationComment.setStatus(Status.ACTIVE);
        accommodationComment.setText(String.valueOf(hostComm.getEditText().getText()));
        accommodationComment.setRating(accommodationRating);

        Call<Comment> callCreateComment = ClientUtils.commentService.createAccommodationComment("Bearer " + accessToken, accommodationComment, detailAccommodation.getId());
        callCreateComment.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.code() == 201) {
                    Log.d("COMMENTS", "Meesage recieved");
                    System.out.println(response.body());
//                    comments = response.body();
                    commentsAdapter=new CommentsAdapter(getActivity(),comments);
                    binding.commentsList.setAdapter(commentsAdapter);
                    enableListScroll(binding.commentsList);
                    commentsAdapter.notifyDataSetChanged();
                    String text="User " + user.getAccount().getUsername()+ " has commented " + detailAccommodation.getName();
                    if(checkNotificationStatus(NotificationType.ACCOMMODATION_RATED)){
                        createNotification(text, NotificationType.ACCOMMODATION_RATED);
                    }
                    Toast.makeText(getActivity(),"Comment created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),"You can't comment on accommodation!", Toast.LENGTH_SHORT).show();
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }


    public void createHostComm() {
        TextInputLayout accommodationComm = binding.hostComment;

        this.guest = new Guest(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(),
                user.getPhoneNumber(), user.getAccount(), user.getPicturePath(),
                user.getLastPasswordResetDate(), user.getActivationLink(), user.getActivationLinkDate());

        Comment accommodationComment = new Comment();
        accommodationComment.setDate(LocalDate.now().toString());
        accommodationComment.setGuest(this.guest);
        accommodationComment.setStatus(Status.ACTIVE);
        accommodationComment.setText(String.valueOf(accommodationComm.getEditText().getText()));
        accommodationComment.setRating(hostRating);

        Call<Comment> callCreateComment = ClientUtils.commentService.createHostComment("Bearer " + accessToken, accommodationComment, detailAccommodation.getHost().getId());
        callCreateComment.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.code() == 201) {
                    String text="User " + user.getAccount().getUsername()+ " has commented you";
                    if(checkNotificationStatus(NotificationType.HOST_RATED)){
                        createNotification(text, NotificationType.HOST_RATED);
                    }
                    Log.d("COMMENTS", "Meesage recieved");
                    Toast.makeText(getActivity(),"Comment created", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(),"You can't comment on host!", Toast.LENGTH_SHORT).show();
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }

    public void showReportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Reason");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reportText = input.getText().toString();
                detailAccommodation.getHost().setReportingReason(reportText);
                reportHost();
            }
        });
        builder.create();
        builder.show();
    }


    public void reportHost() {

        Call<User> callCreateComment = userService.reportUser(detailAccommodation.getHost(), user.getId());
        callCreateComment.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    Log.d("REPORTING REASON", detailAccommodation.getHost().getReportingReason());
                    Toast.makeText(getActivity(),"Reported!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(),"You can't report!", Toast.LENGTH_SHORT).show();
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }

    public void showHostComments() {
        Bundle args = new Bundle();
        args.putParcelable("accommodation", detailAccommodation);
        NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_content_main);
        navController.navigate(R.id.nav_reported_comments,args);
    }

    public void getSettings(){
        Call<HostNotificationSettings> callSettings = notificationService.getHostNotificationSettings("Bearer "+accessToken,detailAccommodation.getHost().getId());
        callSettings.enqueue(new Callback<HostNotificationSettings>() {
            @Override
            public void onResponse(Call<HostNotificationSettings> call, Response<HostNotificationSettings> response) {
                if (response.code() == 200) {
                    hostSettings=response.body();
                    System.out.println(response.body());

                    Log.d("COMMENTS", "Meesage recieved");


                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<HostNotificationSettings> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }

    public boolean checkNotificationStatus(NotificationType type){
        if (NotificationType.RESERVATION_REQUEST==type && hostSettings.isRequestCreated()) {
            return true;
        }
        if (NotificationType.ACCOMMODATION_RATED==type && hostSettings.isAccommodationRated()) {
            return true;
        }
        if (NotificationType.HOST_RATED==type && hostSettings.isRated()) {
            return true;
        }
        return false;
    }
}
