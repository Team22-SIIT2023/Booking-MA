package com.example.booking_team22.clients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.booking_team22.model.UserCredentials;
import com.example.booking_team22.model.UserTokenState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService {

        private UserService authService; // Assuming AuthService is your Retrofit interface

        public AuthenticationService(UserService authService) {
            this.authService = authService;
        }

//        public LiveData<String> authenticateUser(UserCredentials userCredentials) {
//            MutableLiveData<String> resultLiveData = new MutableLiveData<>();
//
//            authService.createAuthenticationToken(userCredentials).enqueue(new Callback<UserTokenState>() {
//                @Override
//                public void onResponse(Call<UserTokenState> call, Response<UserTokenState> response) {
//                    if (response.isSuccessful()) {
//                        UserTokenState userTokenState = response.body();
//                        String accessToken = userTokenState.getAccessToken();
//                        resultLiveData.setValue(accessToken);
//                    } else {
//                        resultLiveData.setValue(null); // or handle authentication failure
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserTokenState> call, Throwable t) {
//                    resultLiveData.setValue(null); // or handle network failure
//                }
//            });
//
//            return resultLiveData;
//        }
public LiveData<UserTokenState> authenticateUser(UserCredentials userCredentials) {
    MutableLiveData<UserTokenState> resultLiveData = new MutableLiveData<>();

    authService.createAuthenticationToken(userCredentials).enqueue(new Callback<UserTokenState>() {
        @Override
        public void onResponse(Call<UserTokenState> call, Response<UserTokenState> response) {
            if (response.isSuccessful()) {
                UserTokenState userTokenState = response.body();
                resultLiveData.setValue(userTokenState);
            } else {
                resultLiveData.setValue(null); // or handle authentication failure
            }
        }

        @Override
        public void onFailure(Call<UserTokenState> call, Throwable t) {
            resultLiveData.setValue(null); // or handle network failure
        }
    });

    return resultLiveData;
}




}
