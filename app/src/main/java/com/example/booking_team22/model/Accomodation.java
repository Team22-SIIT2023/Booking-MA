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

public class Accomodation implements Parcelable, Serializable {

        @SerializedName("id")
        @Expose
        private Long id;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("description")
        @Expose
        private String description;

        @SerializedName("address")
        @Expose
        private Address address;

        @SerializedName("minGuests")
        @Expose
        private int minGuests;

        @SerializedName("maxGuests")
        @Expose
        private int maxGuests;

        @SerializedName("type")
        @Expose
        private AccommodationType type;

        @SerializedName("pricePerGuest")
        @Expose
        private boolean pricePerGuest;

        @SerializedName("automaticConfirmation")
        @Expose
        private boolean automaticConfirmation;

        @SerializedName("host")
        @Expose
        private Host host;

        @SerializedName("status")
        @Expose
        private AccommodationStatus status;

        @SerializedName("reservationDeadline")
        @Expose
        private int reservationDeadline;

        @SerializedName("amenities")
        @Expose
        private ArrayList<Amenity> amenities;

        @SerializedName("priceList")
        @Expose
        private ArrayList<PricelistItem> priceList;

        @SerializedName("freeTimeSlots")
        @Expose
        private ArrayList<TimeSlot> freeTimeSlots;

        public Accomodation(Long id, String name, String description,
                             int minGuests, int maxGuests,
                             boolean pricePerGuest, boolean automaticConfirmation,int reservationDeadline,
                            Address address,AccommodationType type,AccommodationStatus status,
                            ArrayList<Amenity>amenities,ArrayList<PricelistItem> priceList,
                            ArrayList<TimeSlot> freeTimeSlots,Host host) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.minGuests = minGuests;
            this.maxGuests = maxGuests;
            this.pricePerGuest = pricePerGuest;
            this.automaticConfirmation = automaticConfirmation;
            this.reservationDeadline = reservationDeadline;
            this.address=address;
            this.status=status;
            this.type=type;
            this.amenities=amenities;
            this.priceList=priceList;
            this.freeTimeSlots=freeTimeSlots;
            this.host=host;

        }

    public Accomodation() {}

    public Accomodation(Parcel in) {
            id=in.readLong();
            name=in.readString();
            description=in.readString();
            minGuests=in.readInt();
            maxGuests=in.readInt();
            pricePerGuest=in.readBoolean();
            automaticConfirmation=in.readBoolean();
            reservationDeadline=in.readInt();
            address=in.readParcelable(Address.class.getClassLoader());
            status = AccommodationStatus.valueOf(in.readString());
            type = AccommodationType.valueOf(in.readString());
            amenities = in.createTypedArrayList(Amenity.CREATOR);
            freeTimeSlots = in.createTypedArrayList(TimeSlot.CREATOR);
            priceList = in.createTypedArrayList(PricelistItem.CREATOR);
            host=in.readParcelable(Host.class.getClassLoader());
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
//        dest.writeParcelable(address, flags);
        dest.writeString(status.name());
        dest.writeString(type.name());
        dest.writeTypedList(amenities);
        dest.writeTypedList(freeTimeSlots);
        dest.writeTypedList(priceList);
//        dest.writeParcelable(host, flags);
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Host getHost() {
        return host;
    }

    public void setAmenities(ArrayList<Amenity> amenities) {
        this.amenities = amenities;
    }

    public void setPriceList(ArrayList<PricelistItem> priceList) {
        this.priceList = priceList;
    }

    public void setFreeTimeSlots(ArrayList<TimeSlot> freeTimeSlots) {
        this.freeTimeSlots = freeTimeSlots;
    }

    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }

    public ArrayList<PricelistItem> getPriceList() {
        return priceList;
    }

    public ArrayList<TimeSlot> getFreeTimeSlots() {
        return freeTimeSlots;
    }

    public void setType(AccommodationType type) {
        this.type = type;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
    }

    public AccommodationType getType() {
        return type;
    }

    public AccommodationStatus getStatus() {
        return status;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
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

