package com.example.booking_team22.adapters;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

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
    private SharedPreferences sp;
    private String userType;

    public GuestReservationPagerAdapter(Fragment fm,Context context) {
        super(fm);
        sp= context.getApplicationContext().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        userType=sp.getString("userType","");

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(userType.equals("ROLE_GUEST")) {
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
        }else{
            switch (position) {
                case 0:
                    return new GuestReservationListFragment();
                case 1:
                    return new GuestRequestsFragment();
                default:
                    return new GuestReservationListFragment();
            }

        }    }
    public String getPageTitle(int position)
    {
        String title = null;
        if(userType.equals("ROLE_GUEST")){
            if (position == 0)
                title = "Reservations";
            else if (position == 1)
                title = "Requests";
            else if (position == 2)
                title = "Favorites";
            return title;
        }else{
            if (position == 0)
                title = "Reservations";
            else if (position == 1)
                title = "Requests";
            return title;

        }


    }


    @Override
    public int getItemCount() {
        if(userType.equals("ROLE_GUEST")){
            return 3;
        }
        return 2;
    }
}
