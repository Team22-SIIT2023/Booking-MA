package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GuestNotificationSettings extends NotificationSettings implements Parcelable {

    private boolean requestResponded ;

    public boolean isRequestResponded() {
        return requestResponded;
    }

    public void setRequestResponded(boolean requestResponded) {
        this.requestResponded = requestResponded;
    }

    public GuestNotificationSettings(Long id, User user, boolean isRequestResponded) {
        super(id, user);
        this.requestResponded = isRequestResponded;
    }

    protected GuestNotificationSettings(Parcel in) {
        super(in);
        requestResponded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (requestResponded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GuestNotificationSettings> CREATOR = new Creator<GuestNotificationSettings>() {
        @Override
        public GuestNotificationSettings createFromParcel(Parcel in) {
            return new GuestNotificationSettings(in);
        }

        @Override
        public GuestNotificationSettings[] newArray(int size) {
            return new GuestNotificationSettings[size];
        }
    };
}
