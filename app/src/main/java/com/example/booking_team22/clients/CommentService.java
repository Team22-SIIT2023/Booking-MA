package com.example.booking_team22.clients;

import com.example.booking_team22.model.AccommodationComments;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.HostComments;
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

    @GET("comments/accommodations")
    Call<ArrayList<AccommodationComments>> getAccommodationsComments(
            @Header("Authorization") String authorization,@Query("status")String status);

    @GET("comments/hosts")
    Call<ArrayList<HostComments>> getHostsComments(
            @Header("Authorization") String authorization,@Query("status")String status);


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


    @PUT("comments/approve/accommodations/{id}")
    Call<Comment> acceptAccommodationComment(
            @Header("Authorization") String authorization,
            @Path("id") Long id,
            @Body Comment comment
    );

    @PUT("comments/approve/hosts/{id}")
    Call<Comment> acceptHostComment(
            @Header("Authorization") String authorization,
            @Path("id") Long id,
            @Body Comment comment
    );

    @PUT("comments/decline/accommodations/{id}")
    Call<Comment> declineAccommodationComment(
            @Header("Authorization") String authorization,
            @Path("id") Long id,
            @Body Comment comment
    );

    @PUT("comments/decline/hosts/{id}")
    Call<Comment> declineHostComment(
            @Header("Authorization") String authorization,
            @Path("id") Long id,
            @Body Comment comment
    );


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("comments/{id}")
    Call<Comment> deleteComment(@Header("Authorization") String authorization,
                                @Path("id") Long id);

}
