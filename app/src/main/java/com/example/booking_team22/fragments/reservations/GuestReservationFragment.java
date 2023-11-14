package com.example.booking_team22.fragments.reservations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.GuestReservationPagerAdapter;
import com.example.booking_team22.databinding.ActivityHomeBinding;
import com.example.booking_team22.databinding.FragmentGuestReservationBinding;
import com.example.booking_team22.fragments.reservations.guestReservationTabs.GuestFavoritesFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class GuestReservationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TabLayout tabLayout;
    ViewPager viewPager;
    GuestReservationPagerAdapter myViewPagerAdapter;
    FragmentGuestReservationBinding binding;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuestReservationFragment() {
        // Required empty public constructor
    }

    public static GuestReservationFragment newInstance(String param1, String param2) {
        GuestReservationFragment fragment = new GuestReservationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_guest_reservation, container, false);

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        GuestReservationPagerAdapter adapter = new GuestReservationPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Tab " + (position + 1))
        ).attach();

        return view;

    }

}