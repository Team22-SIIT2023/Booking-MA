package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTokenState implements Parcelable {
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("expiresIn")
    @Expose
    private Long expiresIn;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("id")
    @Expose
    private Long id;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public UserTokenState(String accessToken, Long expiresIn, String role, Long id) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.role = role;
        this.id = id;
    }

    protected UserTokenState(Parcel in) {
        accessToken = in.readString();
        expiresIn = in.readLong();
        role = in.readString();
        id = in.readLong();
    }

    public static final Creator<UserTokenState> CREATOR = new Creator<UserTokenState>() {
        @Override
        public UserTokenState createFromParcel(Parcel in) {
            return new UserTokenState(in);
        }

        @Override
        public UserTokenState[] newArray(int size) {
            return new UserTokenState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessToken);
        dest.writeLong(expiresIn);
        dest.writeString(role);
        dest.writeLong(id);
    }
}

