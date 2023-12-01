package com.example.booking_team22.fragments.users.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Notification;

import java.util.ArrayList;

public class AccommodationApprovalFragment extends ListFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ArrayList<Accomodation> accomodations = new ArrayList<Accomodation>();

    AccomodationListAdapter adapter;
    public AccommodationApprovalFragment() {
        // Required empty public constructor
    }
    public static ReportedCommentsFragment newInstance(String param1, String param2) {
        ReportedCommentsFragment fragment = new ReportedCommentsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prepareProductList(accomodations);
        adapter = new AccomodationListAdapter(getActivity(), accomodations);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_accommodation_approval, container, false);
    }

    private void prepareProductList(ArrayList<Accomodation> products){
        products.add(new Accomodation(
                1L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap1));
        products.add(new Accomodation(
                2L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap2));
        products.add(new Accomodation(
                3L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap4));
        products.add(new Accomodation(
                4L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap5));
        products.add(new Accomodation(
                5L,
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                R.drawable.ap6));
    }
}
