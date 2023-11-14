package com.example.booking_team22.fragments.reservations.guestReservationTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.GuestReservationAdapter;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.model.Notification;
import com.example.booking_team22.model.Reservation;

import java.util.ArrayList;

public class GuestReservationListFragment extends ListFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    GuestReservationAdapter adapter;

    public GuestReservationListFragment() {
        // Required empty public constructor
    }
    public static GuestReservationListFragment newInstance(String param1, String param2) {
        GuestReservationListFragment fragment = new GuestReservationListFragment();
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
        prepareReservationsList(reservations);
        adapter = new GuestReservationAdapter(getActivity(), reservations);
        setListAdapter(adapter);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_reservation_list, container, false);
    }
    private void prepareReservationsList(ArrayList<Reservation> reservations){
        reservations.add(new Reservation(
                1L,
                "Accomodation name",
                "Address",
                R.drawable.ap2,
                "15-5-2023-20-5-2023"
        ));
        reservations.add(new Reservation(
                2L,
                "Accomodation name",
                "Address",
                R.drawable.ap1,
                "15-5-2023-26-5-2023"
        ));

    }
}