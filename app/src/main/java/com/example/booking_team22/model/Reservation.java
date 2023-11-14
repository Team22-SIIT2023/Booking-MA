package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Reservation implements Parcelable {
    private Long id;
    private String title;
    private String address;
    private String dates;
    private int image;

    public Reservation(Long id, String title, String address, int image,String dates) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.image = image;
        this.dates=dates;
    }

    public  Reservation() {
    }
    protected Reservation(Parcel in) {
        id = in.readLong();
        title = in.readString();
        address = in.readString();
        image = in.readInt();
        dates=in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", dates='" + dates + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(address);
        dest.writeInt(image);
        dest.writeString(dates);
    }

    public static final Parcelable.Creator<Accomodation> CREATOR = new Parcelable.Creator<Accomodation>() {
        @Override
        public Accomodation createFromParcel(Parcel in) {
            return new Accomodation(in);
        }

        @Override
        public Accomodation[] newArray(int size) {
            return new Accomodation[size];
        }
    };
}
