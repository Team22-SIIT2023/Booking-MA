package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Amenity implements Parcelable {
    long id;
    String amenityName;
    int amenityImage;

    public Amenity(Long id, String amenityName,int image ){
        this.id = id;
        this.amenityName = amenityName;
        this.amenityImage = image;
    }

    public Amenity() {
    }
    protected Amenity(Parcel in) {
        id = in.readLong();
        amenityName = in.readString();
        amenityImage = in.readInt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public void setAmenityName(String title) {
        this.amenityName = title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAmenityImage(int amenityImage) {
        this.amenityImage = amenityImage;
    }

    public int getAmenityImage() {
        return amenityImage;
    }

    @Override
    public String toString() {
        return "Amenity{" +
                "id=" + id +
                ", amenityName='" + amenityName + '\'' +
                ", amenityImage=" + amenityImage +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(amenityName);
        dest.writeInt(amenityImage);
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
