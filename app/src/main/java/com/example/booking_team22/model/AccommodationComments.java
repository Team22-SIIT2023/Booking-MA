package com.example.booking_team22.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class AccommodationComments extends Comment {
    Accomodation accomodation;

    public AccommodationComments(Long id, String text, String date, double rating, Status status, Guest guest, Accomodation accomodation) {
        super(id, text, date, rating, status, guest);
        this.accomodation=accomodation;
    }

    public AccommodationComments(Parcel in) {
        super(in);
        accomodation = in.readParcelable(Accomodation.class.getClassLoader());
    }


    public Accomodation getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(Accomodation accomodation) {
        this.accomodation = accomodation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(accomodation, flags);
    }
}
