package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Comment implements Parcelable {
    private Long id;
    private String email;
    private String commentText;
    private String date;

    public Comment(Long id, String email, String commentText, String date) {
        this.id = id;
        this.email = email;
        this.commentText = commentText;
        this.date = date;
    }

    public Comment() {}

    protected Comment(Parcel in) {
        id = in.readLong();
        email = in.readString();
        commentText = in.readString();
        date = in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(email);
        dest.writeString(commentText);
        dest.writeString(date);
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
