package com.example.booking_team22.clients;

import android.content.Context;

import com.example.booking_team22.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {
    private static final String SERVICE_API_PATH = "http://" + BuildConfig.IP_ADDR + ":8080/api/";

    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }

    /*
     * Prvo je potrebno da definisemo retrofit instancu preko koje ce komunikacija ici
     * */
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(test())
            .build();

    /*
     * Definisemo konkretnu instancu servisa na intnerntu sa kojim
     * vrsimo komunikaciju
     * */
    public static AccommodationService accommodationService = retrofit.create(AccommodationService.class);
    public static CommentService commentService = retrofit.create(CommentService.class);
    public static UserService userService = retrofit.create(UserService.class);
    public static RequestService requestService=retrofit.create(RequestService.class);
    public static ReportService reportService=retrofit.create(ReportService.class);
    public static NotificationService notificationService=retrofit.create(NotificationService.class);

    public static AmenityService amenityService=retrofit.create(AmenityService.class);

    public static AuthenticationService authenticationService = new AuthenticationService(userService);


}
