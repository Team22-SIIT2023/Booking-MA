package com.example.booking_team22.clients;

import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Amenity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AmenityService {
    @GET("amenities")
    Call<ArrayList<Amenity>> getAll(
            @Header("Authorization") String authorization
    );
}
