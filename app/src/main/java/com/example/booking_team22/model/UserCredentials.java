package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserCredentials implements Parcelable, Serializable {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Parcelable implementation
    protected UserCredentials(Parcel in) {
        username = in.readString();
        password = in.readString();
    }

    public static final Parcelable.Creator<UserCredentials> CREATOR = new Creator<UserCredentials>() {
        @Override
        public UserCredentials createFromParcel(Parcel in) {
            return new UserCredentials(in);
        }

        @Override
        public UserCredentials[] newArray(int size) {
            return new UserCredentials[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
    }
}
