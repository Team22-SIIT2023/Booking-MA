package com.example.booking_team22.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class HostComments extends Comment{
    Host host;
    public HostComments(Long id, String text, String date, double rating, Status status, Guest guest, Host host) {
        super(id, text, date, rating, status, guest);
        this.host = host;
    }

    protected HostComments(Parcel in) {
        super(in);
        host = in.readParcelable(Host.class.getClassLoader());
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(host, flags);
    }
}
