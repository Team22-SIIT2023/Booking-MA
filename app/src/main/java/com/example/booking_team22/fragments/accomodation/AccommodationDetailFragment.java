package com.example.booking_team22.fragments.accomodation;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AmenityListAdapter;
import com.example.booking_team22.adapters.CommentsAdapter;
import com.example.booking_team22.databinding.FragmentAccommodationDetailBinding;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.Comment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class AccommodationDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;


    private ArrayList<Amenity> amenities = new ArrayList<Amenity>();
    private ArrayList<Comment> comments=new ArrayList<>();
    CommentsAdapter commentsAdapter;
    AmenityListAdapter adapter;
    private MapView mapView;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private GoogleMap googleMap;

    FragmentAccommodationDetailBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccommodationDetailFragment() {
        // Required empty public constructor
    }
    public static AccommodationDetailFragment newInstance() {
        return new AccommodationDetailFragment();
    }

    public static AccommodationDetailFragment newInstance(String param1, String param2) {
        AccommodationDetailFragment fragment = new AccommodationDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

                            input.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        binding = FragmentAccommodationDetailBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        prepareProductList(amenities);
        adapter = new AmenityListAdapter(getActivity(), amenities);
        binding.amenityList.setAdapter(adapter);

        prepareCommentsList(comments);
        commentsAdapter=new CommentsAdapter(getActivity(),comments);
        binding.commentsList.setAdapter(commentsAdapter);

        LinearLayout linearLayout = binding.layoutPictures;

        int[] imageResources = {R.drawable.ap1,R.drawable.ap2,R.drawable.ap5};

        for (int resourceId : imageResources) {
            ImageView imageView = new ImageView(getActivity());
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

        Integer[] arraySpinner = new Integer[] {
                1, 2,3,4,5
        };
        Spinner s = (Spinner) binding.spinner;
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        return root;
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
