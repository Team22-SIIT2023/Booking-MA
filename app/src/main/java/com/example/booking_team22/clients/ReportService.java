package com.example.booking_team22.clients;

import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Report;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ReportService {
    @GET("reports")
    Call<ArrayList<Report>> getRangeReport(
            @Header("Authorization") String authorization,
            @Query("hostId") Long hostId,
            @Query("begin") String begin,
            @Query("end") String end
    );
    @GET("reports/annual")
    Call<Report> getAnnualReport(
            @Header("Authorization") String authorization,
            @Query("accommodationName") String accommodationName,
            @Query("year") int year
    );

}
