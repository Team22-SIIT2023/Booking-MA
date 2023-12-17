package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Accomodation implements Parcelable {

        @SerializedName("id")
        @Expose
        private Long id;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("description")
        @Expose
        private String description;

//        @SerializedName("address")
//        @Expose
//        private Address address;

        @SerializedName("minGuests")
        @Expose
        private int minGuests;

        @SerializedName("maxGuests")
        @Expose
        private int maxGuests;

//        @SerializedName("type")
//        @Expose
//        private AccommodationType type;

        @SerializedName("pricePerGuest")
        @Expose
        private boolean pricePerGuest;

        @SerializedName("automaticConfirmation")
        @Expose
        private boolean automaticConfirmation;

//        @SerializedName("host")
//        @Expose
//        private Host host;

//        @SerializedName("status")
//        @Expose
//        private AccommodationStatus status;

        @SerializedName("reservationDeadline")
        @Expose
        private int reservationDeadline;

//        @SerializedName("amenities")
//        @Expose
//        private ArrayList<Amenity> amenities;

//        @SerializedName("priceList")
//        @Expose
//        private ArrayList<PricelistItem> priceList;
//
//        @SerializedName("freeTimeSlots")
//        @Expose
//        private ArrayList<TimeSlot> freeTimeSlots;

        public Accomodation(Long id, String name, String description,
                             int minGuests, int maxGuests,
                             boolean pricePerGuest, boolean automaticConfirmation,int reservationDeadline
                            ) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.minGuests = minGuests;
            this.maxGuests = maxGuests;
            this.pricePerGuest = pricePerGuest;
            this.automaticConfirmation = automaticConfirmation;
            this.reservationDeadline = reservationDeadline;

        }

    public Accomodation(Parcel in) {
            id=in.readLong();
            name=in.readString();
            description=in.readString();
            minGuests=in.readInt();
            maxGuests=in.readInt();
            pricePerGuest=in.readBoolean();
            automaticConfirmation=in.readBoolean();
            reservationDeadline=in.readInt();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public boolean isPricePerGuest() {
        return pricePerGuest;
    }

    public void setPricePerGuest(boolean pricePerGuest) {
        this.pricePerGuest = pricePerGuest;
    }

    public boolean isAutomaticConfirmation() {
        return automaticConfirmation;
    }

    public void setAutomaticConfirmation(boolean automaticConfirmation) {
        this.automaticConfirmation = automaticConfirmation;
    }

    public int getReservationDeadline() {
        return reservationDeadline;
    }

    public void setReservationDeadline(int reservationDeadline) {
        this.reservationDeadline = reservationDeadline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(minGuests);
        dest.writeInt(maxGuests);
        dest.writeBoolean(pricePerGuest);
        dest.writeBoolean(automaticConfirmation);
        dest.writeInt(reservationDeadline);
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
//    private Long id;
//    private String title;
//    private String description;
//    private int image;
//    private int icon=0;
//
//    public Accomodation(Long id, String title, String description, int image, int icons) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.image = image;
//        this.icon = icons;
//    }
//
//    public Accomodation(Long id, String title, String description, int image) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.image = image;
//    }
//
//    public Accomodation() {
//    }
//    // Konstruktor za čitanje iz Parcel objekta
//    protected Accomodation(Parcel in) {
//        // Čitanje ostalih atributa proizvoda iz Parcel objekta
//        id = in.readLong();
//        title = in.readString();
//        description = in.readString();
//        image = in.readInt();
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public int getIcon() {
//        return icon;
//    }
//
//    public void setIcon(int icon) {
//        this.icon = icon;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }
//
//    @Override
//    public String toString() {
//        return "Accomodation{" +
//                "title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", image='" + image + '\'' +
//                '}';
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeLong(id);
//        dest.writeString(title);
//        dest.writeString(description);
//        dest.writeInt(image);
//        dest.writeInt(icon);
//    }
//
//    public static final Creator<Accomodation> CREATOR = new Creator<Accomodation>() {
//        @Override
//        public Accomodation createFromParcel(Parcel in) {
//            return new Accomodation(in);
//        }
//
//        @Override
//        public Accomodation[] newArray(int size) {
//            return new Accomodation[size];
//        }
//    };

