package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Accomodation implements Parcelable {
    private Long id;
    private String title;
    private String description;
    private int image;

    public Accomodation(Long id, String title, String description, int image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Accomodation() {
    }
    // Konstruktor za čitanje iz Parcel objekta
    protected Accomodation(Parcel in) {
        // Čitanje ostalih atributa proizvoda iz Parcel objekta
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        image = in.readInt();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
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
        dest.writeString(description);
        dest.writeInt(image);
    }

    public static final Creator<Accomodation> CREATOR = new Creator<Accomodation>() {
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
