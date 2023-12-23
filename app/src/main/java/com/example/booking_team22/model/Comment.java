package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;

public class Comment implements Parcelable, Serializable {

    private Long id;
    private String text;
    private String date;
    private double rating;
    private Status status;
    private Guest guest;

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public double getRating() {
        return rating;
    }

    public Status getStatus() {
        return status;
    }

    public Guest getGuest() {
        return guest;
    }

    public Comment(Long id, String text, String date, double rating, Status status, Guest guest) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.rating = rating;
        this.status = status;
        this.guest = guest;
    }

    protected Comment(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        text = in.readString();
        rating = in.readDouble();
        guest = in.readParcelable(Guest.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
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
        dest.writeString(text);
        dest.writeDouble(rating);
        dest.writeParcelable(guest, flags);
    }
}
