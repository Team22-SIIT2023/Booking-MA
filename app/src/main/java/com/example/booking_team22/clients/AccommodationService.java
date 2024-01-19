package com.example.booking_team22.clients;

import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.PricelistItem;
import com.example.booking_team22.model.TimeSlot;
import com.example.booking_team22.model.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccommodationService {
//    @Headers({
//            "User-Agent: Mobile-Android",
//            "Content-Type:application/json"
//    })
    @GET("accommodations")
    Call<ArrayList<Accomodation>> getAll(
            @Header("Authorization") String authorization,
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
  
//    @Headers({
//            "User-Agent: Mobile-Android",
//            "Content-Type:application/json"
//    })
    @GET("accommodations/{accommodationId}/images")
    Call<List<String>> getImages(@Header("Authorization") String authorization, @Path("accommodationId") Long accommodationId);

  
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodations")
    Call<Accomodation> createAccommodation(@Header("Authorization") String authorization, @Body Accomodation accomodation);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/editPricelist/{id}")
    Call<Accomodation> editPrice(@Header("Authorization") String authorization, @Body PricelistItem pricelistItem, @Path("id") Long id);


    @PUT("accommodations/changeFreeTimeSlots/{accommodationId}")
    Call<Accomodation> changeFreeTimeSlots(
            @Header("Authorization") String authorization,
            @Path("accommodationId") Long accommodationId,
            @Body TimeSlot reservationTimeSlot
    );

//    @Headers({
//            "User-Agent: Mobile-Android",
//            "Content-Type:application/json"
//    })
    @GET("accommodations/{id}")
    Call<Accomodation> getById(@Header("Authorization") String authorization, @Path("id") Long id);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @PUT("accommodations/editTimeSlot/{id}")
    Call<Accomodation> editFreeTimeslots(@Header("Authorization") String authorization, @Body TimeSlot timeSlot, @Path("id") Long id);


//    @Headers({
//            "User-Agent: Mobile-Android",
//            "Content-Type:application/json"
//    })
    @GET("accommodations/calculatePrice/{id}")
    Call<Double> calculatePrice(
            @Header("Authorization") String authorization,
            @Path("id") Long id,
            @Query("guestNumber") int guestNumber,
            @Query("begin") String begin,
            @Query("end") String end);

    @PUT("accommodations/approval")
    Call<Accomodation> updateAccommodationRequestApproval( @Header("Authorization") String authorization,
                                                           @Body Accomodation accomodation);


    @PUT("accommodations/{id}")
    Call<Accomodation> updateAccommodation(
            @Header("Authorization") String authorization,
            @Body Accomodation accomodation,
            @Path("id") Long id);

    @PUT("accommodations/accept/{id}")
    Call<Accomodation> accept(
            @Header("Authorization") String authorization,
            @Body  Accomodation acceptingAccommodation,
            @Path("id") Long id);

    @PUT("accommodations/decline/{id}")
    Call<Accomodation> decline(
            @Header("Authorization") String authorization,
            @Body Accomodation decliningAccommodation,
            @Path("id") Long id);

    @Multipart
    @POST("accommodations/{accommodationId}/upload-picture")
    Call<Accomodation> uploadImages(
            @Header("Authorization") String authorization,
            @Part() MultipartBody.Part[] images,
            @Path("accommodationId") Long accommodationId
    );
}
