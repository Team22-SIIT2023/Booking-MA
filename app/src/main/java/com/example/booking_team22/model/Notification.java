package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class Notification implements Parcelable {

    private Long id;

    private User user;

    private String text;

    private String date;

    private NotificationType type;

    private boolean deleted = Boolean.FALSE;

    public Notification(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Notification(Long id, User user, String text, String date, NotificationType type, boolean deleted) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.date = date;
        this.type = type;
        this.deleted = deleted;
    }
    public Notification(User user, String text, String date, NotificationType type) {
        this.user = user;
        this.text = text;
        this.date = date;
        this.type = type;
    }

    protected Notification(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        user = in.readParcelable(User.class.getClassLoader());
        text = in.readString();
        deleted = in.readByte() != 0;
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
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
        dest.writeParcelable(user, flags);
        dest.writeString(text);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }
}