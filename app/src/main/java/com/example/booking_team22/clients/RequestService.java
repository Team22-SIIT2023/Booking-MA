package com.example.booking_team22.clients;

import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.RequestStatus;
import com.example.booking_team22.model.ReservationRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("requests")
    Call<ReservationRequest> createRequest(@Body ReservationRequest reservationRequest);

    @GET("requests")
    Call<ArrayList<ReservationRequest>> getAll(
            @Query("status") RequestStatus status,
            @Query("begin") String begin,
            @Query("end") String end,
            @Query("accommodationName") String accommodationName
    );
}
