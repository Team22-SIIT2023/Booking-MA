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
    TabLayout tabLayout;
    ViewPager viewPager;
    GuestReservationPagerAdapter myViewPagerAdapter;
    FragmentGuestReservationBinding binding;


    public GuestReservationFragment() {
        // Required empty public constructor
    }

    public static GuestReservationFragment newInstance(String param1, String param2) {
        GuestReservationFragment fragment = new GuestReservationFragment();
        Bundle args = new Bundle();
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
                (tab, position) -> tab.setText(adapter.getPageTitle(position))
        ).attach();

        return view;

    }

}