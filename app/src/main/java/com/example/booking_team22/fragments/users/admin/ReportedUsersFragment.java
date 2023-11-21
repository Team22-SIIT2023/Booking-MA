package com.example.booking_team22.fragments.users.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.CommentsAdapter;
import com.example.booking_team22.adapters.UserListAdapter;
import com.example.booking_team22.fragments.account.AccountFragment;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.User;

import java.util.ArrayList;

public class ReportedUsersFragment extends ListFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ArrayList<User> users = new ArrayList<User>();

    UserListAdapter adapter;
    public ReportedUsersFragment() {
        // Required empty public constructor
    }
    public static ReportedUsersFragment newInstance(String param1, String param2) {
        ReportedUsersFragment fragment = new ReportedUsersFragment();
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
        prepareProductList(users);
        adapter = new UserListAdapter(getActivity(), users);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_reported_user, container, false);
    }

    private void prepareProductList(ArrayList<User> users) {
        users.add(new User(
                "isidoraaleksic@yahoo.com","isidoricaslatkica", User.UserStatus.ACTIVE
        ));
        users.add(new User(
                "isidoraaleksic@yahoo.com","isidoricaslatkica", User.UserStatus.ACTIVE
        ));
        users.add(new User(
                "isidoraaleksic@yahoo.com","isidoricaslatkica", User.UserStatus.ACTIVE
        ));
        users.add(new User(
                "isidoraaleksic@yahoo.com","isidoricaslatkica", User.UserStatus.ACTIVE
        ));
    }
}
