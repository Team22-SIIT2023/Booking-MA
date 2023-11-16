package com.example.booking_team22.fragments.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends ListFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Notification> notifications = new ArrayList<Notification>();
    NotificationListAdapter adapter;
    private String mParam1;
    private String mParam2;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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

        prepareProductList(notifications);
        adapter = new NotificationListAdapter(getActivity(), notifications);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    private void prepareProductList(ArrayList<Notification> notifications){
        notifications.add(new Notification(
                1L,
                "15-5-2023",
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                ));
        notifications.add(new Notification(
                1L,
                "15-5-2023",
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        ));
        notifications.add(new Notification(
                1L,
                "15-5-2023",
                "Accomodation name",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        ));
    }
}