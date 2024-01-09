package com.example.booking_team22.fragments.reports;

import static android.content.Context.MODE_PRIVATE;

import static com.example.booking_team22.clients.ClientUtils.accommodationService;
import static com.example.booking_team22.clients.ClientUtils.reportService;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;


import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import androidx.core.content.FileProvider;
import com.github.mikephil.charting.charts.BarChart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import com.example.booking_team22.R;
import com.example.booking_team22.databinding.FragmentReportsBinding;
import com.example.booking_team22.model.Report;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.layout.element.Paragraph;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportsFragment extends Fragment {
    private int mYear, mMonth, mDay, mHour, mMinute;

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

        download.setOnClickListener(v -> {
            downloadReportPdf();
        });


        return root;
    }


    private void downloadReportPdf() {
        Bitmap bitmap1 = createBitmapFromChart(binding.barChart);
        Bitmap bitmap2 = createBitmapFromChart(binding.barChartSecond);
        saveBitmapAsPdf(bitmap1,bitmap2,"report.pdf");
    }

    private void saveBitmapAsPdf(Bitmap bitmap1, Bitmap bitmap2, String fileName) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName + ".pdf");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            ContentResolver resolver = requireActivity().getContentResolver();
            Uri pdfUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

            if (pdfUri != null) {
                OutputStream outputStream = resolver.openOutputStream(pdfUri);

                if (outputStream != null) {
                    PdfWriter pdfWriter = new PdfWriter(outputStream);
                    PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                    Document document = new Document(pdfDocument);

                    Image image1 = new Image(ImageDataFactory.create(bitmapToByteArray(bitmap1)));
                    document.add(image1);

                    document.add(new Paragraph("\n"));

                    if(binding.barChartSecond.getVisibility()==View.VISIBLE){
                        Image image2 = new Image(ImageDataFactory.create(bitmapToByteArray(bitmap2)));
                        document.add(image2);
                    }

                    document.close();
                    outputStream.close();
                }

                Intent openPdfIntent = new Intent(Intent.ACTION_VIEW);
                openPdfIntent.setDataAndType(pdfUri, "application/pdf");
                openPdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                try {
                    startActivity(openPdfIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Bitmap createBitmapFromChart(BarChart barChart) {
        barChart.measure(
                View.MeasureSpec.makeMeasureSpec(barChart.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(barChart.getMeasuredHeight(), View.MeasureSpec.EXACTLY)
        );
        barChart.layout(0, 0, barChart.getMeasuredWidth(), barChart.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(barChart.getWidth(), barChart.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        barChart.draw(canvas);

        return bitmap;
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    private void drawYearlyGraph() {
        BarChart barChart = binding.barChart;
        binding.barChartSecond.setVisibility(View.GONE);

        double[] profitByMonth = yearlyReport.getProfitByMonth();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < profitByMonth.length; i++) {
            entries.add(new BarEntry(i, (float) profitByMonth[i]));
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Monthly Profits");
        barDataSet.setColor( ContextCompat.getColor(requireContext(), R.color.darkPurple));

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return months[(int) value];
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularityEnabled(true);


        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        barChart.invalidate();
    }

    private void drawRangeGraph() {
        binding.barChartSecond.setVisibility(View.VISIBLE);

        BarChart profitChart = binding.barChart;
        ArrayList<BarEntry> profitEntries = new ArrayList<>();
        ArrayList<String> accNames = new ArrayList<>();

        for (int i = 0; i < reports.size(); i++) {
            profitEntries.add(new BarEntry(i, reports.get(i).getTotalProfit()));
            accNames.add(reports.get(i).getAccommodationName());

        }

        BarDataSet profitDataSet = new BarDataSet(profitEntries, "Profits");
        profitDataSet.setColor( ContextCompat.getColor(requireContext(), R.color.darkPurple));

        BarData profitData = new BarData(profitDataSet);
        profitData.setBarWidth(0.6f);
        profitChart.setData(profitData);
        profitChart.getDescription().setEnabled(false);


        XAxis xAxis = profitChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(accNames.size(),false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = Math.round(value);
                if(index >= accNames.size()){
                    index = accNames.size()-1;
                }
                return accNames.get(index);
            }
        });
        YAxis leftAxis = profitChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        YAxis rightAxis = profitChart.getAxisRight();
        rightAxis.setEnabled(false);

        BarChart reservationsChart = binding.barChartSecond;
        ArrayList<BarEntry> reservationEntries = new ArrayList<>();

        for (int i = 0; i < reports.size(); i++) {
            reservationEntries.add(new BarEntry(i, reports.get(i).getNumberOfReservations()));
        }

        BarDataSet reservationDataSet = new BarDataSet(reservationEntries, "Reservations");
        reservationDataSet.setColor( ContextCompat.getColor(requireContext(), R.color.darkPurple));

        BarData reservationData = new BarData(reservationDataSet);
        reservationData.setBarWidth(0.6f);
        reservationsChart.setData(reservationData);
        reservationsChart.getDescription().setEnabled(false);


        XAxis xAxisSecond = reservationsChart.getXAxis();
        xAxisSecond.setGranularity(1f);
        xAxisSecond.setGranularityEnabled(true);
        xAxisSecond.setLabelCount(accNames.size(),false);
        xAxisSecond.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisSecond.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = Math.round(value);
                if(index >= accNames.size()){
                    index = accNames.size()-1;
                }
                return accNames.get(index);
            }
        });
        YAxis leftAxisSecond = reservationsChart.getAxisLeft();
        leftAxisSecond.setAxisMinimum(0f);
        leftAxisSecond.setDrawGridLines(true);
        YAxis rightAxisSecond= reservationsChart.getAxisRight();
        rightAxisSecond.setEnabled(false);

        profitChart.invalidate();
        reservationsChart.invalidate();

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
                    drawYearlyGraph();

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
                    drawRangeGraph();

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