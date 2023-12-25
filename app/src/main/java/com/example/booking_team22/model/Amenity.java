package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Amenity implements Parcelable, Serializable {

    @SerializedName("id")
    @Expose
    long id;

    @SerializedName("amenity_name")
    @Expose
    String name;
//    int amenityImage;

    public Amenity(Long id, String amenityName){
        this.id = id;
        this.name = amenityName;
//        this.amenityImage = image;
    }

    public Amenity(String amenityName){
        this.name = amenityName;
    }

    public Amenity() {
    }
    protected Amenity(Parcel in) {
        id = in.readLong();
        name = in.readString();
//        amenityImage = in.readInt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmenityName() {
        return name;
    }

    public void setAmenityName(String title) {
        this.name = title;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public void setAmenityImage(int amenityImage) {
//        this.amenityImage = amenityImage;
//    }
//
//    public int getAmenityImage() {
//        return amenityImage;
//    }

    @Override
    public String toString() {
        return "Amenity{" +
                "id=" + id +
                ", amenityName='" + name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
//        dest.writeInt(amenityImage);
    }

    public static final Parcelable.Creator<Amenity> CREATOR = new Parcelable.Creator<Amenity>() {
        @Override
        public Amenity createFromParcel(Parcel in) {
            return new Amenity(in);
        }

        @Override
        public Amenity[] newArray(int size) {
            return new Amenity[size];
        }
    };
}
