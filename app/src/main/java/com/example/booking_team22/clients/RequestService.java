package com.example.booking_team22.clients;

import com.example.booking_team22.model.ReservationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("requests")
    Call<ReservationRequest> createRequest(@Body ReservationRequest reservationRequest);
}
