package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Parcelable, Serializable {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("postalCode")
    @Expose
    private String postalCode;


    public Address(Long id, String address, String country, String city, String postalCode) {
        this.id = id;
        this.address = address;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Address() {}

    public Address(String address, String country, String city) {
        this.address = address;
        this.country = country;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(address);
        dest.writeString(postalCode);
    }

    protected Address(Parcel in) {
        id = in.readLong();
        address = in.readString();
        city = in.readString();
        country = in.readString();
        postalCode = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
