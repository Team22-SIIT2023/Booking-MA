package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReservationRequest implements Parcelable, Serializable {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("timeSlot")
    @Expose
    private TimeSlot timeSlot;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("guest")
    @Expose
    private Guest guest;
    @SerializedName("guestNumber")
    @Expose
    private int guestNumber;
    @SerializedName("accommodation")
    @Expose
    private Accomodation accommodation;
    @SerializedName("status")
    @Expose
    private RequestStatus status;


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

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;
    }

    public Accomodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accomodation accommodation) {
        this.accommodation = accommodation;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public ReservationRequest( TimeSlot timeSlot, double price, Guest guest, int guestNumber, Accomodation accommodation, RequestStatus status) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
        this.guest = guest;
        this.guestNumber = guestNumber;
        this.accommodation = accommodation;
        this.status = status;
    }

    protected ReservationRequest(Parcel in) {
//        if (in.readByte() == 0) {
//            id = null;
//        } else {
//            id = in.readLong();
//        }
        timeSlot = in.readParcelable(TimeSlot.class.getClassLoader());
        price = in.readDouble();
        guest = in.readParcelable(Guest.class.getClassLoader());
        guestNumber = in.readInt();
        accommodation = in.readParcelable(Accomodation.class.getClassLoader());
    }

    public static final Creator<ReservationRequest> CREATOR = new Creator<ReservationRequest>() {
        @Override
        public ReservationRequest createFromParcel(Parcel in) {
            return new ReservationRequest(in);
        }

        @Override
        public ReservationRequest[] newArray(int size) {
            return new ReservationRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        if (id == null) {
//            dest.writeByte((byte) 0);
//        } else {
//            dest.writeByte((byte) 1);
//            dest.writeLong(id);
//        }
        dest.writeParcelable(timeSlot, flags);
        dest.writeDouble(price);
        dest.writeParcelable(guest, flags);
        dest.writeInt(guestNumber);
        dest.writeParcelable(accommodation, flags);
    }
}
