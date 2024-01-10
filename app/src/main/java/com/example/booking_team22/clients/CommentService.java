package com.example.booking_team22.clients;

import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Status;
import com.example.booking_team22.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
            @Header("Authorization") String authorization,
            @Path("accommodationId") Long accommodationId,
            @Query("status") String status
    );


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("comments/host/{hostId}")
    Call<ArrayList<Comment>> getHostComments(
            @Header("Authorization") String authorization,
            @Path("hostId") Long accommodationId,
            @Query("status") String status
    );


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("comments/accommodation/{accommodationId}/averageRate")
    Call<Double> getAccommodationRating(
            @Header("Authorization") String authorization,
            @Path("accommodationId") Long accommodationId
    );


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("comments/accommodation/{accommodationId}")
    Call<Comment> createAccommodationComment(@Header("Authorization") String authorization,
                                             @Body Comment comment,
                                             @Path("accommodationId") Long id);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("comments/host/{hostId}")
    Call<Comment> createHostComment(@Header("Authorization") String authorization,
                                    @Body Comment comment,
                                    @Path("hostId") Long id);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("comments/reportComment/{commentId}")
    Call<Comment> reportComment(@Header("Authorization") String authorization,
                                @Body Status status,
                                @Path("commentId") Long id);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("comments/{id}")
    Call<Comment> deleteComment(@Header("Authorization") String authorization,
                                @Path("id") Long id);

}
