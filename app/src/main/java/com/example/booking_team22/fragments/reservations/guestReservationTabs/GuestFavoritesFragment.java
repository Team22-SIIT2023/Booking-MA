package com.example.booking_team22.fragments.reservations.guestReservationTabs;

import static android.content.Context.MODE_PRIVATE;

import static com.example.booking_team22.clients.ClientUtils.userService;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.databinding.FragmentGuestFavoritesBinding;
import com.example.booking_team22.model.Accomodation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestFavoritesFragment extends ListFragment {
    private ArrayList<Accomodation> favorites = new ArrayList<Accomodation>();
    AccomodationListAdapter adapter;
    FragmentGuestFavoritesBinding binding;
    private SharedPreferences sp;
    private String accessToken;
    private Long userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGuestFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sp = getActivity().getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");
        userId=sp.getLong("userId",0L);
        getFavorites();

        return root;
    }

    private void getFavorites() {

        Call<ArrayList<Accomodation>> call = userService.getFavorites("Bearer " + accessToken, userId);
                call.enqueue(new Callback<ArrayList<Accomodation>>() {
            @Override
            public void onResponse(Call<ArrayList<Accomodation>> call, Response<ArrayList<Accomodation>> response) {
                if (response.code() == 200) {
                    favorites = response.body();
                    for(Accomodation accomodation:favorites){
                            accomodation.setPrice(0.0);
                            accomodation.setUnitPrice(0.0);
                    }
                    adapter = new AccomodationListAdapter(getActivity(), favorites);
                    ListView listView=binding.list;
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