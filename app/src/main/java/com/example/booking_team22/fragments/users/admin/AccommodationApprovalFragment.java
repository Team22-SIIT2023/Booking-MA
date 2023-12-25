package com.example.booking_team22.fragments.users.admin;

import static com.example.booking_team22.clients.ClientUtils.accommodationService;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.databinding.FragmentAccommodationApprovalBinding;
import com.example.booking_team22.databinding.FragmentAccomodationPageBinding;
import com.example.booking_team22.model.AccommodationStatus;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Notification;
import com.example.booking_team22.model.Status;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationApprovalFragment extends ListFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ArrayList<Accomodation> accommodations = new ArrayList<Accomodation>();

    private ArrayList<Accomodation> createdAccommodations = new ArrayList<Accomodation>();

    private ArrayList<Accomodation> updatedAccommodations = new ArrayList<Accomodation>();
    AccomodationListAdapter adapter;

    FragmentAccommodationApprovalBinding binding;
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
        binding = FragmentAccommodationApprovalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
//        return inflater.inflate(R.layout.fragment_accommodation_approval, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Call<ArrayList<Accomodation>> call = accommodationService.getAll(
                null, null, 0, null, 0, 0,"CREATED", null, null, null, null
        );

        call.enqueue(new Callback<ArrayList<Accomodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accomodation>> call, Response<ArrayList<Accomodation>> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Meesage recieved");
                    System.out.println("rispons bodi" + response.body());
                    createdAccommodations = response.body();
                    accommodations.addAll(createdAccommodations);
//                    adapter = new AccomodationListAdapter(getActivity(), createdAccommodations);
                    //setListAdapter(adapter);
//                    ListView listView = binding.list;
//                    listView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("REZ", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Accomodation>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });


        Call<ArrayList<Accomodation>> call1 = accommodationService.getAll(
                null, null, 0, null, 0, 0,"UPDATED", null, null, null, null
        );
        call1.enqueue(new Callback<ArrayList<Accomodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accomodation>> call, Response<ArrayList<Accomodation>> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Meesage recieved");
                    System.out.println("rispons bodi" + response.body());
                    updatedAccommodations = response.body();
                    accommodations.addAll(updatedAccommodations);
                    adapter = new AccomodationListAdapter(getActivity(), accommodations);
                    //setListAdapter(adapter);
                    ListView listView = binding.list;
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("REZ", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Accomodation>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

}
