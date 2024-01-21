package com.example.booking_team22.fragments.notifications;

import static com.example.booking_team22.clients.ClientUtils.amenityService;
import static com.example.booking_team22.clients.ClientUtils.notificationService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.adapters.AmenityListAdapter;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.databinding.FragmentAccomodationPageBinding;
import com.example.booking_team22.databinding.FragmentNotificationsBinding;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Amenity;
import com.example.booking_team22.model.Notification;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends ListFragment {

    private ArrayList<Notification> notifications = new ArrayList<Notification>();
    NotificationListAdapter adapter;

    private FragmentNotificationsBinding binding;
    private SharedPreferences sp;
    private String accessToken;
    private Long userId;


    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences("mySharedPrefs", Context.MODE_PRIVATE);

        accessToken = sp.getString("accessToken", "");
        userId=sp.getLong("userId",0L);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        loadNotifications();

        return root;
    }

    private void loadNotifications() {
        Call<ArrayList<Notification>> call = notificationService.getUserNotifications("Bearer "+accessToken,userId);
        call.enqueue(new Callback<ArrayList<Notification>>() {
            @Override
            public void onResponse(Call<ArrayList<Notification>> call, Response<ArrayList<Notification>> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    System.out.println(response.body());

                    notifications = response.body();
                    adapter = new NotificationListAdapter(getActivity(), notifications);
                    binding.list.setAdapter(adapter);


                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Notification>> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }

}