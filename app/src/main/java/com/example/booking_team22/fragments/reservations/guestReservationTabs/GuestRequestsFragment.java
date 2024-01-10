package com.example.booking_team22.fragments.reservations.guestReservationTabs;

import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.requestService;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.adapters.GuestRequestAdapter;
import com.example.booking_team22.databinding.FragmentGuestRequestsBinding;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.RequestStatus;
import com.example.booking_team22.model.Reservation;
import com.example.booking_team22.model.ReservationRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestRequestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestRequestsFragment extends ListFragment {
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    private SharedPreferences sp;
    private String accessToken;

    GuestRequestAdapter adapter;
    FragmentGuestRequestsBinding binding;

    private String startDate=null;
    private String endDate=null;
    private String accName=null;
    private RequestStatus status=null;
    private Long userId=null;
    public GuestRequestsFragment() {
        // Required empty public constructor
    }
    public static GuestRequestsFragment newInstance(String param1, String param2) {
        GuestRequestsFragment fragment = new GuestRequestsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGuestRequestsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button search=binding.btnAcceptFilters;

        sp = getActivity().getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");

        userId=sp.getLong("userId",0L);

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);

        String[] arraySpinner = new String[RequestStatus.values().length+1];
        arraySpinner[0] = "";
        for (int i = 0; i < RequestStatus.values().length; i++) {
            arraySpinner[i+1] = RequestStatus.values()[i].name().toString();
        }
        Spinner statusSpinner = (Spinner) binding.statusSpinner;
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        if(sp.getString("userType", "").equals("ROLE_HOST")){
            getRequestsForHost(userId,null, null, null, null);
        }else {
            getRequestsForGuest(userId,null, null, null, null);
        }


//        status = statusSpinner.getSelectedItem().toString();
//        TextInputEditText startDateInput = binding.cicoInput;
//        startDate = startDateInput.getText().toString();
//        TextInputEditText endDateInput =binding.cicoInput2;
//        endDate = endDateInput.getText().toString();
//        EditText name = binding.accNameText;
//        accName=name.getText().toString();

        search.setOnClickListener(v -> {
            String statusString = statusSpinner.getSelectedItem().toString();
            if(statusString.equals("")){
                status=null;
            }else{
                status=RequestStatus.valueOf(statusString);
            }
            TextInputEditText startDateInput = binding.cicoInput;
            startDate = startDateInput.getText().toString();
            TextInputEditText endDateInput =binding.cicoInput2;
            endDate = endDateInput.getText().toString();
            EditText name = binding.accNameText;
            accName=name.getText().toString();
            if(sp.getString("userType", "").equals("ROLE_HOST")){
                getRequestsForHost(userId,status, startDate, endDate, accName);
            }else {
                getRequestsForGuest(userId,status, startDate, endDate, accName);
            }
                });


//        adapter = new GuestRequestAdapter(getActivity(), reservations);
//        setListAdapter(adapter);


        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        binding.cicoInput.setText("");
        binding.cicoInput2.setText("");
        binding.accNameText.setText("");
        binding.statusSpinner.setSelection(0);
        if(sp.getString("userType", "").equals("ROLE_HOST")){
            getRequestsForHost(userId,null, null, null, null);
        }else {
            getRequestsForGuest(userId,null, null, null, null);
        }

    }

    private void getRequestsForHost(Long userId, RequestStatus status, String begin, String end,String name) {
        Call<ArrayList<ReservationRequest>> call = requestService.getAllForHost("Bearer " + accessToken,userId,status, begin, end, name);
        call.enqueue(new Callback<ArrayList<ReservationRequest>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationRequest>> call, Response<ArrayList<ReservationRequest>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ReservationRequest> requests = response.body();
                    adapter = new GuestRequestAdapter(getActivity(), requests);
                    ListView listView=binding.list;
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
    private void getRequestsForGuest(Long userId, RequestStatus status, String begin, String end,String name) {
        Call<ArrayList<ReservationRequest>> call = requestService.getAllForGuest("Bearer " + accessToken,userId,status, begin, end, name);
        call.enqueue(new Callback<ArrayList<ReservationRequest>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationRequest>> call, Response<ArrayList<ReservationRequest>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ReservationRequest> requests = response.body();
                    adapter = new GuestRequestAdapter(getActivity(), requests);
                    ListView listView=binding.list;
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

    private void setDate(TextInputEditText input) {
        input.setOnClickListener(v->{
            final Calendar c = Calendar.getInstance();

            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            String formattedDate = String.format(Locale.US, "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                            input.setText(formattedDate);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }


    private void prepareReservationsList(ArrayList<Reservation> reservations){
        reservations.add(new Reservation(
                1L,
                "Accomodation name",
                "Address",
                R.drawable.ap2,
                "15-5-2023-20-5-2023",
                R.drawable.ic_cancel
        ));
        reservations.add(new Reservation(
                2L,
                "Accomodation name",
                "Address",
                R.drawable.ap1,
                "15-5-2023-26-5-2023",
                R.drawable.ic_aacept
        ));
        reservations.add(new Reservation(
                2L,
                "Accomodation name",
                "Address",
                R.drawable.ap1,
                "15-5-2023-26-5-2023",
                R.drawable.ic_loading
        ));
    }
}