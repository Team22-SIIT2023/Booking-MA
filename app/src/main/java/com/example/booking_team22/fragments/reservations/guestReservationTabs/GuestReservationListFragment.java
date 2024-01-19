package com.example.booking_team22.fragments.reservations.guestReservationTabs;

import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.requestService;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.GuestRequestAdapter;
import com.example.booking_team22.adapters.GuestReservationAdapter;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.databinding.FragmentGuestRequestsBinding;
import com.example.booking_team22.databinding.FragmentGuestReservationBinding;
import com.example.booking_team22.databinding.FragmentGuestReservationListBinding;
import com.example.booking_team22.fragments.reservations.GuestReservationFragment;
import com.example.booking_team22.model.Notification;
import com.example.booking_team22.model.RequestStatus;
import com.example.booking_team22.model.Reservation;
import com.example.booking_team22.model.ReservationRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestReservationListFragment extends ListFragment {

    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    private SharedPreferences sp;
    private String accessToken;
    private String userType;

    GuestRequestAdapter adapter;
    FragmentGuestReservationListBinding binding;

    public GuestReservationListFragment() {
        // Required empty public constructor
    }
    public static GuestReservationListFragment newInstance(String param1, String param2) {
        GuestReservationListFragment fragment = new GuestReservationListFragment();
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
        binding = FragmentGuestReservationListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sp = getActivity().getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");
        userType = sp.getString("userType","");
        long id = sp.getLong("userId",0L);

        if(userType.equals("ROLE_GUEST")) {
            Call<ArrayList<ReservationRequest>> call = requestService.getAllForGuest("Bearer " + accessToken,id, RequestStatus.ACCEPTED, null, null, null);
            call.enqueue(new Callback<ArrayList<ReservationRequest>>() {
                @Override
                public void onResponse(Call<ArrayList<ReservationRequest>> call, Response<ArrayList<ReservationRequest>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<ReservationRequest> requests = response.body();
                        adapter = new GuestRequestAdapter(getActivity(), requests);
                        ListView listView = binding.list;
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ReservationRequest>> call, Throwable t) {
                    // Handle network errors or unexpected failures
                    t.printStackTrace();
                }
            });
        }
        else{
            Call<ArrayList<ReservationRequest>> call = requestService.getAllForHost("Bearer " + accessToken, id, RequestStatus.ACCEPTED, null, null, null);
            call.enqueue(new Callback<ArrayList<ReservationRequest>>() {
                @Override
                public void onResponse(Call<ArrayList<ReservationRequest>> call, Response<ArrayList<ReservationRequest>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<ReservationRequest> requests = response.body();
                        adapter = new GuestRequestAdapter(getActivity(), requests);
                        ListView listView = binding.list;
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ReservationRequest>> call, Throwable t) {
                    // Handle network errors or unexpected failures
                    t.printStackTrace();
                }
            });
        }


        return root;

    }
}