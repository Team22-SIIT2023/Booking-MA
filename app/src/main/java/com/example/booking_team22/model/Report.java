package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Report implements Parcelable, Serializable {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("accommodationId")
    @Expose
    private Long accommodationId;
    @SerializedName("accommodationName")
    @Expose
    private String accommodationName;
    @SerializedName("totalProfit")
    @Expose
    private int totalProfit;

    @SerializedName("profitByMonth")
    @Expose
    private double[] profitByMonth;

    @SerializedName("numberOfReservations")
    @Expose
    private int numberOfReservations;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public int getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(int totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double[] getProfitByMonth() {
        return profitByMonth;
    }

    public void setProfitByMonth(double[] profitByMonth) {
        this.profitByMonth = profitByMonth;
    }

    public int getNumberOfReservations() {
        return numberOfReservations;
    }

    public void setNumberOfReservations(int numberOfReservations) {
        this.numberOfReservations = numberOfReservations;
    }

    public Report(Long id, Long accommodationId, String accommodationName, int totalProfit, double[] profitByMonth, int numberOfReservations) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.accommodationName = accommodationName;
        this.totalProfit = totalProfit;
        this.profitByMonth = profitByMonth;
        this.numberOfReservations = numberOfReservations;
    }

    protected Report(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            accommodationId = null;
        } else {
            accommodationId = in.readLong();
        }
        accommodationName = in.readString();
        totalProfit = in.readInt();
        profitByMonth = in.createDoubleArray();
        numberOfReservations = in.readInt();
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (accommodationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(accommodationId);
        }
        dest.writeString(accommodationName);
        dest.writeInt(totalProfit);
        dest.writeDoubleArray(profitByMonth);
        dest.writeInt(numberOfReservations);
    }
}
