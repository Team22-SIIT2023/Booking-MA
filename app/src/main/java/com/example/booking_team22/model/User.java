package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String email;
    private String password;
    private UserStatus userStatus;

    // Enum for user status
    public enum UserStatus {
        ACTIVE,
        BLOCKED,
        REPORTED
    }

    // Constructor
    public User(String email, String password, UserStatus userStatus) {
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        email = in.readString();
        password = in.readString();
        userStatus = UserStatus.valueOf(in.readString());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(userStatus.name());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
