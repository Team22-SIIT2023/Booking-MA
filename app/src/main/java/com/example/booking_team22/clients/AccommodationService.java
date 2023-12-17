package com.example.booking_team22.clients;

import com.example.booking_team22.model.Accomodation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface AccommodationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations")
    Call<ArrayList<Accomodation>> getAll();
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/{accommodationId}/images")
    Call<List<String>> getImages(@Path("accommodationId") Long accommodationId);
}
