package com.example.booking_team22.fragments.reports;

import static android.content.Context.MODE_PRIVATE;

import static com.example.booking_team22.clients.ClientUtils.reportService;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.booking_team22.databinding.FragmentReportsBinding;
import com.example.booking_team22.model.Report;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportsFragment extends Fragment {
    private int mYear, mMonth, mDay;

    private SharedPreferences sp;
    private String accessToken;

    FragmentReportsBinding binding;

    private String startDate = null;
    private String endDate = null;
    private int year = 0;
    private String accName = null;
    private Long userId = null;

    private ArrayList<Report> reports = new ArrayList<Report>();
    private Report yearlyReport;


    public ReportsFragment() {
    }


    public static ReportsFragment newInstance() {
        ReportsFragment fragment = new ReportsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sp = getActivity().getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");

        userId = sp.getLong("userId", 0L);

        setDate(binding.cicoInput);
        setDate(binding.cicoInput2);

        Spinner nameSpinner = (Spinner) binding.accNameSpinner;
        List<String> arraySpinner = new ArrayList<>();  //load from backend!
        arraySpinner.add("");
        arraySpinner.add("Accommodation Name 1");
        arraySpinner.add("Accommodation Name 3");
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        namesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(namesAdapter);

        Button rangeReport = binding.rangeReportBtn;
        Button yearlyReport = binding.yearlyReportBtn;
        Button download = binding.downloadBtn;

        rangeReport.setOnClickListener(v -> {
            TextInputEditText startDateInput = binding.cicoInput;
            startDate = startDateInput.getText().toString();
            TextInputEditText endDateInput = binding.cicoInput2;
            endDate = endDateInput.getText().toString();
            generateRangeReport(startDate, endDate);
        });
        yearlyReport.setOnClickListener(v -> {
            EditText yearInput = binding.yearInput;
            year = Integer.parseInt(yearInput.getText().toString());
            String nameString = nameSpinner.getSelectedItem().toString();
            generateYearlyReport(year, nameString);
        });


        return root;
    }


    private void generateYearlyReport(int year, String nameString) {
        Call<Report> call = reportService.getAnnualReport( "Bearer " + accessToken,nameString,year
        );
        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Meesage recieved");
                    yearlyReport= response.body();

                } else {
                    Log.d("REZ", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

    private void generateRangeReport(String startDate, String endDate) {
        Call<ArrayList<Report>> call = reportService.getRangeReport( "Bearer " + accessToken,userId,
                startDate,endDate
        );
        call.enqueue(new Callback<ArrayList<Report>>() {
            @Override
            public void onResponse(Call<ArrayList<Report>> call, Response<ArrayList<Report>> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Meesage recieved");
                    reports = response.body();
                    System.out.println(reports);

                } else {
                    Log.d("REZ", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Report>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.cicoInput.setText("");
        binding.cicoInput2.setText("");
        binding.yearInput.setText("");
        binding.accNameSpinner.setSelection(0);
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
}