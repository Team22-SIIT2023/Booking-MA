package com.example.booking_team22.fragments.reservations.guestReservationTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.GuestFavoriteAdapter;
import com.example.booking_team22.adapters.GuestReservationAdapter;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Reservation;

import java.util.ArrayList;

public class GuestFavoritesFragment extends ListFragment {
    private ArrayList<Accomodation> favorites = new ArrayList<Accomodation>();
    GuestFavoriteAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prepareReservationsList(favorites);
        adapter = new GuestFavoriteAdapter(getActivity(), favorites);
        setListAdapter(adapter);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_favorites, container, false);
    }

    private void prepareReservationsList(ArrayList<Accomodation> reservations){
        favorites.add(new Accomodation(
                1L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap2,
                R.drawable.ic_favourite
        ));
        reservations.add(new Accomodation(
                2L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap1,
                R.drawable.ic_favourite
        ));
    }
}