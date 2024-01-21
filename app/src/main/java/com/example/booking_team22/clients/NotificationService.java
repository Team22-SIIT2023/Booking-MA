package com.example.booking_team22.clients;

import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.GuestNotificationSettings;
import com.example.booking_team22.model.HostNotificationSettings;
import com.example.booking_team22.model.Notification;
import com.example.booking_team22.model.NotificationSettings;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotificationService {
    @GET("notifications/{userId}")
    Call<ArrayList<Notification>> getUserNotifications(@Header("Authorization") String authorization, @Path("userId") Long id);

    @GET("notifications/new/{userId}")
    Call<Notification> getNewNotifications(@Header("Authorization") String authorization, @Path("userId") Long id);

    @POST("notifications")
    Call<Notification> createUserNotification(@Header("Authorization") String authorization, @Body Notification notification);

    @GET("notifications/guest/settings/{userId}")
    Call<GuestNotificationSettings> getGuestNotificationSettings(@Header("Authorization") String authorization, @Path("userId") Long id);
    @GET("notifications/host/settings/{userId}")
    Call<HostNotificationSettings> getHostNotificationSettings(@Header("Authorization") String authorization, @Path("userId") Long id);

    @PUT("notifications/{userId}/guestSettings")
    Call<GuestNotificationSettings> updateGuestNotificationSettings(@Header("Authorization") String authorization,
                                                       @Path("userId") Long id,
                                                       @Body GuestNotificationSettings guestNotificationSettings);

    @PUT("notifications/{userId}/hostSettings")
    Call<HostNotificationSettings> updateHostNotificationSettings(@Header("Authorization") String authorization,
                                                       @Path("userId") Long id,
                                                       @Body HostNotificationSettings hostNotificationSettings);

    @PUT("notifications/{notificationId}")
    Call<Notification> updateNotification(@Header("Authorization") String authorization,
                                                                  @Path("notificationId") Long id,
                                                                  @Body Notification notification);


}
