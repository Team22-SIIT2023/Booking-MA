package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Accomodation implements Parcelable {
    private Long id;
    private String title;
    private String description;
    private int image;
    private int icon=0;

    private boolean buttonVisibility;

    public Accomodation(Long id, String title, String description, int image, int icons, boolean buttonVisibility) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.icon = icons;
        this.buttonVisibility = buttonVisibility;
    }

    public Accomodation(Long id, String title, String description, int image, boolean buttonVisibility) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.buttonVisibility = buttonVisibility;
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
        buttonVisibility = in.readByte() != 0;
    }

    public boolean isButtonVisible() {
        return buttonVisibility;
    }

    public void setButtonVisible(boolean buttonVisibility) {
        this.buttonVisibility = buttonVisibility;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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
        return "Accomodation{" +
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
        dest.writeInt(icon);
        dest.writeByte((byte) (buttonVisibility ? 1 : 0));
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
