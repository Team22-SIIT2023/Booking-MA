package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;

public class TimeSlot implements Parcelable,Serializable {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("endDate")
    @Expose
    private String endDate;

    public TimeSlot(Long id, String startDate, String endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TimeSlot(String startDate, String endDate) {
//        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected TimeSlot(Parcel in) {
        id = in.readLong();
        startDate = in.readString();
        endDate = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeSerializable(startDate);
        dest.writeSerializable(endDate);
    }

    public TimeSlot() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
//                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public static final Creator<TimeSlot> CREATOR = new Creator<TimeSlot>() {
        @Override
        public TimeSlot createFromParcel(Parcel in) {
            return new TimeSlot(in);
        }

        @Override
        public TimeSlot[] newArray(int size) {return new TimeSlot[size];
        }
    };
}
