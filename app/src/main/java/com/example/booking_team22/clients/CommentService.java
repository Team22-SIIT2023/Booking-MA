package com.example.booking_team22.clients;

import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Comment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("comments")
    Call<ArrayList<Accomodation>> getAll();
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("comments/accommodation/{accommodationId}")
    Call<ArrayList<Comment>> getAccommodationComments(
            @Path("accommodationId") Long accommodationId,
            @Query("status") String status
    );
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("comments/accommodation/{accommodationId}/averageRate")
    Call<Double> getAccommodationRating(
            @Path("accommodationId") Long accommodationId
    );

}
