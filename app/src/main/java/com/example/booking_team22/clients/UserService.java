package com.example.booking_team22.clients;

import com.example.booking_team22.model.Host;
import com.example.booking_team22.model.User;
import com.example.booking_team22.model.UserCredentials;
import com.example.booking_team22.model.UserTokenState;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("users/{id}")
    Call<User> getUser(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("users/login")
    Call<UserTokenState> createAuthenticationToken(@Body UserCredentials userCredentials);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("users/signup")
    Call<User> signup(@Body User user);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("host/{id}")
    Call<Host> getHost(@Path("id") Long id);
}
