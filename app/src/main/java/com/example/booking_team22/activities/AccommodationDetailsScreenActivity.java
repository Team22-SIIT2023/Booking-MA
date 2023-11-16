package com.example.booking_team22.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AmenityListAdapter;
import com.example.booking_team22.adapters.CommentsAdapter;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.databinding.ActivityAccommodationDetailsBinding;
import com.example.booking_team22.databinding.ActivityHomeBinding;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Notification;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class AccommodationDetailsScreenActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;


    private ArrayList<Amenity> amenities = new ArrayList<Amenity>();
    private ArrayList<Comment> comments=new ArrayList<>();
    CommentsAdapter commentsAdapter;
    AmenityListAdapter adapter;
    private MapView mapView;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private GoogleMap googleMap;

    ActivityAccommodationDetailsBinding binding;

    public AccommodationDetailsScreenActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        binding = ActivityAccommodationDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);


        prepareProductList(amenities);
        adapter = new AmenityListAdapter(this, amenities);
        binding.amenityList.setAdapter(adapter);

        prepareCommentsList(comments);
        commentsAdapter=new CommentsAdapter(this,comments);
        binding.commentsList.setAdapter(commentsAdapter);

        LinearLayout linearLayout = findViewById(R.id.layoutPictures);

        int[] imageResources = {R.drawable.ap1,R.drawable.ap2,R.drawable.ap5};

        for (int resourceId : imageResources) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(resourceId);

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

        enableListScroll(binding.amenityList);
        enableListScroll(binding.commentsList);
        Button myButton = binding.btnAddComments;
        myButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add,0,0,0);

        mapView=binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap1 -> {
            LatLng markerLatLng=new LatLng(47.5189687,18.9606965);
            googleMap1.addMarker(new MarkerOptions().position(markerLatLng).title("Marker title"));
            googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,12));
        });
        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);


        NumberPicker numberPicker = binding.numberPicker;
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(1);

        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            updateNumberText(newVal);
        });
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_accomodations) {
            navigateToOldActivityFragment(HomeActivity.FRAGMENT_ACCOMMODATIONS);
            return true;
        }

        if (itemId == R.id.nav_reservations) {
            navigateToOldActivityFragment(HomeActivity.FRAGMENT_RESERVATIONS);
            return true;
        }

        if (itemId == R.id.nav_notifications) {
            navigateToOldActivityFragment(HomeActivity.FRAGMENT_NOTIFICATIONS);
            return true;
        }
        if (itemId == R.id.nav_account) {
            navigateToOldActivityFragment(HomeActivity.FRAGMENT_ACCOUNT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void navigateToOldActivityFragment(int fragmentId) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("fragmentId", fragmentId);
        startActivity(intent);
    }


    private void prepareProductList(ArrayList<Amenity> amenities){
        amenities.add(new Amenity(
                1L,
                "Wifi",
                R.drawable.ic_wifi
        ));
        amenities.add(new Amenity(
                1L,
                "Air conditioning",
                R.drawable.ic_air
        ));
        amenities.add(new Amenity(
                1L,
                "Room service",
                R.drawable.ic_room_service
        ));
        amenities.add(new Amenity(
                1L,
                "Pets",
                R.drawable.ic_pets
        ));
        amenities.add(new Amenity(
                1L,
                "Pool",
                R.drawable.ic_pool
        ));
    }
    private void prepareCommentsList(ArrayList<Comment> comments){
        comments.add(new Comment(
                1L,
                "a@gmail.com",
                "Comment text....",
                "12.12.2023."
        ));
        comments.add(new Comment(
                2L,
                "a@gmail.com",
                "Comment text....",
                "12.12.2023."
        ));
        comments.add(new Comment(
                3L,
                "a@gmail.com",
                "Comment text....",
                "12.12.2023."
        ));
        comments.add(new Comment(
                4L,
                "a@gmail.com",
                "Comment text....",
                "12.12.2023."
        ));
    }
}
