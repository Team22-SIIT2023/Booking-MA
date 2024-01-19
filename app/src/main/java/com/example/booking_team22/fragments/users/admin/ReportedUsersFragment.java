package com.example.booking_team22.fragments.users.admin;

import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.userService;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.CommentsAdapter;
import com.example.booking_team22.adapters.UserListAdapter;
import com.example.booking_team22.databinding.FragmentReportedCommentBinding;
import com.example.booking_team22.databinding.FragmentReportedUserBinding;
import com.example.booking_team22.fragments.account.AccountFragment;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedUsersFragment extends ListFragment {

    private ArrayList<User> users = new ArrayList<User>();

    UserListAdapter adapter;

    private SharedPreferences sp;
    private String userType;
    private String accessToken;

    FragmentReportedUserBinding binding;
    public ReportedUsersFragment() {
        // Required empty public constructor
    }
    public static ReportedUsersFragment newInstance(String param1, String param2) {
        ReportedUsersFragment fragment = new ReportedUsersFragment();
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
        binding = FragmentReportedUserBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        sp = getActivity().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");

        userType = sp.getString("userType","");
        long id = sp.getLong("userId",0L);

        Call<ArrayList<User>> callUser = userService.getUsersByStatus("Bearer " + accessToken,"REPORTED");
        callUser.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.code() == 200) {
                    Log.d("USER", "Message received");
                    users = response.body();
                    adapter = new UserListAdapter(getActivity(), users);
                    setListAdapter(adapter);
                } else {
                    Log.d("USER_REQUEST", "Message received: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.e("USER_REQUEST", "Error: " + t.getMessage(), t);
            }
        });

        return root;
    }

}
