package com.example.booking_team22.clients;

import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.PricelistItem;
import com.example.booking_team22.model.TimeSlot;
import com.example.booking_team22.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @POST("accommodations")
    Call<Accomodation> createAccommodation(@Body Accomodation accomodation);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/editPricelist/{id}")
    Call<Accomodation> editPrice(@Body PricelistItem pricelistItem, @Path("id") Long id);
  
  
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

    @PUT("accommodations/editTimeSlot/{id}")
    Call<Accomodation> editFreeTimeslots(@Body TimeSlot timeSlot, @Path("id") Long id);


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


    @PUT("accommodations/{id}")
    Call<Accomodation> updateAccommodation(@Body Accomodation accomodation, @Path("id") Long id);

    @PUT("accommodations/accept/{id}")
    Call<Accomodation> accept(@Body  Accomodation acceptingAccommodation,@Path("id") Long id);

    @PUT("accommodations/decline/{id}")
    Call<Accomodation> decline(@Body Accomodation decliningAccommodation, @Path("id") Long id);
}
