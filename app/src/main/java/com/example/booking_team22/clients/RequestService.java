package com.example.booking_team22.clients;

import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.RequestStatus;
import com.example.booking_team22.model.ReservationRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestService {

//    @Headers({
//            "User-Agent: Mobile-Android",
//            "Content-Type:application/json"
//    })
    @POST("requests")
    Call<ReservationRequest> createRequest( @Header("Authorization") String authorization,
                                            @Body ReservationRequest reservationRequest);

//    @GET("requests")
//    Call<ArrayList<ReservationRequest>> getAll(
//            @Header("Authorization") String authorization,
//            @Query("status") RequestStatus status,
//            @Query("begin") String begin,
//            @Query("end") String end,
//            @Query("accommodationName") String accommodationName
//    );
    @GET("requests/host/{hostId}")
    Call<ArrayList<ReservationRequest>> getAllForHost(
            @Header("Authorization") String authorization,
            @Path("hostId") Long hostId,
            @Query("status") RequestStatus status,
            @Query("begin") String begin,
            @Query("end") String end,
            @Query("accommodationName") String accommodationName
    );
    @GET("requests/guest/{guestId}")
    Call<ArrayList<ReservationRequest>> getAllForGuest(
            @Header("Authorization") String authorization,
            @Path("guestId") Long guestId,
            @Query("status") RequestStatus status,
            @Query("begin") String begin,
            @Query("end") String end,
            @Query("accommodationName") String accommodationName
    );
    @DELETE("requests/{id}")
    Call<ReservationRequest>deleteRequest(
            @Header("Authorization") String authorization,
            @Path("id") Long id
    );
    @GET("requests/{guestId}/cancelledReservations")
    Call<Integer> getCancellationsForGuest(
            @Header("Authorization") String authorization,
            @Path("guestId") Long guestId
    );

}
