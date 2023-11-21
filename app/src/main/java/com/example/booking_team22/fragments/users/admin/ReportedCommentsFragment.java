package com.example.booking_team22.fragments.users.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.adapters.CommentsAdapter;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Comment;

import java.util.ArrayList;

public class ReportedCommentsFragment extends ListFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ArrayList<Comment> comments = new ArrayList<Comment>();

    CommentsAdapter adapter;
    public ReportedCommentsFragment() {
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
        prepareProductList(comments);
        adapter = new CommentsAdapter(getActivity(), comments);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_reported_comment, container, false);
//        return inflater.inflate(R.layout.fragment_reported_comment, container, false);
    }

    private void prepareProductList(ArrayList<Comment> comments){
        comments.add(new Comment(
                1L,
                "a@gmail.com",
                "Comment text....",
                "12.12.2023.",
                true
        ));
        comments.add(new Comment(
                2L,
                "a@gmail.com",
                "Comment text....",
                "12.12.2023.",
                true
        ));
        comments.add(new Comment(
                3L,
                "a@gmail.com",
                "Comment text....",
                "12.12.2023.",
                true
        ));
        comments.add(new Comment(
                4L,
                "a@gmail.com",
                "Comment text....",
                "12.12.2023.",
                true
        ));
    }
}
