package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationSettings implements Parcelable {

    Long id;
    User user;

    public NotificationSettings(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    protected NotificationSettings(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        user = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationSettings> CREATOR = new Creator<NotificationSettings>() {
        @Override
        public NotificationSettings createFromParcel(Parcel in) {
            return new NotificationSettings(in);
        }

        @Override
        public NotificationSettings[] newArray(int size) {
            return new NotificationSettings[size];
        }
    };
}
