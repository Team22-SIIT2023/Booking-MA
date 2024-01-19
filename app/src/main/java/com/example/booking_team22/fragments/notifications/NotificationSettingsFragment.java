package com.example.booking_team22.fragments.notifications;

import static com.example.booking_team22.clients.ClientUtils.notificationService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.NotificationListAdapter;
import com.example.booking_team22.databinding.FragmentNotificationSettingsBinding;
import com.example.booking_team22.databinding.FragmentNotificationsBinding;
import com.example.booking_team22.model.GuestNotificationSettings;
import com.example.booking_team22.model.HostNotificationSettings;
import com.example.booking_team22.model.Notification;
import com.example.booking_team22.model.NotificationSettings;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationSettingsFragment extends Fragment {

    private FragmentNotificationSettingsBinding binding;
    private SharedPreferences sp;
    private String accessToken;
    private String userType;
    private Long userId;
    HostNotificationSettings hostNotificationSettings;
    GuestNotificationSettings guestNotificationSettings;


    public NotificationSettingsFragment() {
        // Required empty public constructor
    }

    public static NotificationSettingsFragment newInstance() {
        NotificationSettingsFragment fragment = new NotificationSettingsFragment();
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
        userType=sp.getString("userType","");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btnSave=binding.btnSaveSettings;

        if(Objects.equals(userType, "ROLE_HOST")){
            binding.switchRespondedRequest.setVisibility(View.GONE);
        }else{
            binding.switchCancelledReservation.setVisibility(View.GONE);
            binding.switchCommentAccommodation.setVisibility(View.GONE);
            binding.switchCommentHost.setVisibility(View.GONE);
            binding.switchRequestMade.setVisibility(View.GONE);
        }
        loadSettings();

        btnSave.setOnClickListener(v -> {
            saveSettings();
        });



        return root;
    }

    private void saveSettings() {
        if(Objects.equals(userType, "ROLE_HOST")){
            saveHostSettings();
        }else{
            saveGuestSettings();
        }
    }

    private void saveGuestSettings() {
        guestNotificationSettings.setRequestResponded(binding.switchRespondedRequest.isChecked());

        Call<GuestNotificationSettings> call = notificationService.updateGuestNotificationSettings("Bearer "+accessToken,userId,guestNotificationSettings);
        call.enqueue(new Callback<GuestNotificationSettings>() {
            @Override
            public void onResponse(Call<GuestNotificationSettings> call, Response<GuestNotificationSettings> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    //System.out.println(response.body());

                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<GuestNotificationSettings> call, Throwable t) {
                Log.e("Usao ovde", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void saveHostSettings() {
        hostNotificationSettings.setAccommodationRated(binding.switchCommentAccommodation.isChecked());
        hostNotificationSettings.setRated(binding.switchCommentHost.isChecked());
        hostNotificationSettings.setReservationCancelled(binding.switchCancelledReservation.isChecked());
        hostNotificationSettings.setRequestCreated(binding.switchRequestMade.isChecked());


        Call<HostNotificationSettings> call = notificationService.updateHostNotificationSettings("Bearer "+accessToken,userId,hostNotificationSettings);
        call.enqueue(new Callback<HostNotificationSettings>() {
            @Override
            public void onResponse(Call<HostNotificationSettings> call, Response<HostNotificationSettings> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    //System.out.println(response.body());

                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<HostNotificationSettings> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void loadSettings() {

        if(Objects.equals(userType, "ROLE_HOST")){
           loadHostSettings();
        }else{
           loadGuestSettings();
        }

    }

    private void loadGuestSettings() {
        Call<GuestNotificationSettings> call = notificationService.getGuestNotificationSettings("Bearer "+accessToken,userId);
        call.enqueue(new Callback<GuestNotificationSettings>() {
            @Override
            public void onResponse(Call<GuestNotificationSettings> call, Response<GuestNotificationSettings> response) {
                if (response.code() == 200) {
                    guestNotificationSettings=response.body();
                    binding.switchRespondedRequest.setChecked(guestNotificationSettings.isRequestResponded());

                    Log.d("COMMENTS", "Meesage recieved");
                    System.out.println(response.body());

                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<GuestNotificationSettings> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void loadHostSettings() {
        Call<HostNotificationSettings> call = notificationService.getHostNotificationSettings("Bearer "+accessToken,userId);
        call.enqueue(new Callback<HostNotificationSettings>() {
            @Override
            public void onResponse(Call<HostNotificationSettings> call, Response<HostNotificationSettings> response) {
                if (response.code() == 200) {
                    hostNotificationSettings=response.body();
                    System.out.println(response.body());

                    binding.switchRequestMade.setChecked(hostNotificationSettings.isRequestCreated());
                    binding.switchCommentHost.setChecked(hostNotificationSettings.isRated());
                    binding.switchCommentAccommodation.setChecked(hostNotificationSettings.isAccommodationRated());
                    binding.switchCancelledReservation.setChecked(hostNotificationSettings.isReservationCancelled());

                    Log.d("COMMENTS", "Meesage recieved");


                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<HostNotificationSettings> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }
}