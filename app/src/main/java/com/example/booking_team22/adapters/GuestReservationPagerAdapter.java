package com.example.booking_team22.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.booking_team22.fragments.reservations.GuestReservationFragment;
import com.example.booking_team22.fragments.reservations.guestReservationTabs.GuestFavoritesFragment;
import com.example.booking_team22.fragments.reservations.guestReservationTabs.GuestRequestsFragment;
import com.example.booking_team22.fragments.reservations.guestReservationTabs.GuestReservationListFragment;

import java.util.ArrayList;
import java.util.List;


public class GuestReservationPagerAdapter  extends FragmentStateAdapter {

    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> stringList=new ArrayList<>();
    public GuestReservationPagerAdapter(Fragment fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GuestReservationListFragment();
            case 1:
                return new GuestRequestsFragment();
            case 2:
                return new GuestFavoritesFragment();
            default:
                return new GuestReservationListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
