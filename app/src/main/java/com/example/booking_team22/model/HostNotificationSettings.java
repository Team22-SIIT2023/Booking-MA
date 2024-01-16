package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HostNotificationSettings extends NotificationSettings implements Parcelable {

    private boolean requestCreated;
    private boolean reservationCancelled ;
    private boolean rated;
    private boolean accommodationRated;

    public boolean isRequestCreated() {
        return requestCreated;
    }

    public void setRequestCreated(boolean requestCreated) {
        this.requestCreated = requestCreated;
    }

    public boolean isReservationCancelled() {
        return reservationCancelled;
    }

    public void setReservationCancelled(boolean reservationCancelled) {
        this.reservationCancelled = reservationCancelled;
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public boolean isAccommodationRated() {
        return accommodationRated;
    }

    public void setAccommodationRated(boolean accommodationRated) {
        this.accommodationRated = accommodationRated;
    }

    public HostNotificationSettings(Long id, User user, boolean isRequestCreated, boolean isReservationCancelled, boolean isRated, boolean isAccommodationRated) {
        super(id, user);
        this.requestCreated = isRequestCreated;
        this.reservationCancelled = isReservationCancelled;
        this.rated = isRated;
        this.accommodationRated = isAccommodationRated;
    }

    protected HostNotificationSettings(Parcel in) {
        super(in);
        requestCreated = in.readByte() != 0;
        reservationCancelled = in.readByte() != 0;
        rated = in.readByte() != 0;
        accommodationRated = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (requestCreated ? 1 : 0));
        dest.writeByte((byte) (reservationCancelled ? 1 : 0));
        dest.writeByte((byte) (rated ? 1 : 0));
        dest.writeByte((byte) (accommodationRated ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HostNotificationSettings> CREATOR = new Creator<HostNotificationSettings>() {
        @Override
        public HostNotificationSettings createFromParcel(Parcel in) {
            return new HostNotificationSettings(in);
        }

        @Override
        public HostNotificationSettings[] newArray(int size) {
            return new HostNotificationSettings[size];
        }
    };
}
