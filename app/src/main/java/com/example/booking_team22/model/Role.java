package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Role implements Parcelable, Serializable {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }

    protected Role(Parcel in) {
        id = in.readLong();
        name=in.readString();
    }

    @Override
    public String toString() {
        return name;
    }

    public static final Creator<Role> CREATOR = new Parcelable.Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };
}
