package com.example.booking_team22.clients;

import com.example.booking_team22.model.Accomodation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccommodationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations")
    Call<ArrayList<Accomodation>> getAll(
            @Query("begin") String begin,
            @Query("end") String end,
            @Query("guestNumber") int guestNumber,
            @Query("type") String type,
            @Query("start_price") double startPrice,
            @Query("end_price") double endPrice,
            @Query("status") String status,
            @Query("country") String country,
            @Query("city") String city,
            @Query("amenities") List<String> amenities,
            @Query("hostId") Integer hostId
    );
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/{accommodationId}/images")
    Call<List<String>> getImages(@Path("accommodationId") Long accommodationId);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/{id}")
    Call<Accomodation> getById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/calculatePrice/{id}")
    Call<Double> calculatePrice(
            @Path("id") Long id,
            @Query("guestNumber") int guestNumber,
            @Query("begin") String begin,
            @Query("end") String end);
}