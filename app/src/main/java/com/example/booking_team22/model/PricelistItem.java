package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PricelistItem implements Parcelable,Serializable {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("timeSlot")
    @Expose
    private TimeSlot timeSlot;

    @SerializedName("price")
    @Expose
    private double price;

    public PricelistItem(Long id, TimeSlot timeSlot, double price) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
    }

    public PricelistItem(TimeSlot timeSlot, double price) {
        this.timeSlot = timeSlot;
        this.price = price;
    }

    protected PricelistItem(Parcel in) {
        id = in.readLong();
        price=in.readDouble();
        timeSlot=in.readParcelable(TimeSlot.class.getClassLoader());
    }

    public PricelistItem() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeDouble(price);
        dest.writeParcelable(timeSlot, flags);

    }

    public static final Creator<PricelistItem> CREATOR = new Creator<PricelistItem>() {
        @Override
        public PricelistItem createFromParcel(Parcel in) {
            return new PricelistItem(in);
        }

        @Override
        public PricelistItem[] newArray(int size) {return new PricelistItem[size];
        }
    };
}
