package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDate;

public class User implements Parcelable, Serializable {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("account")
    @Expose
    private Account account;

    @SerializedName("picturePath")
    @Expose
    private String picturePath;

    @SerializedName("lastPasswordResetDate")
    @Expose
    private String lastPasswordResetDate;

    @SerializedName("activationLink")
    @Expose
    private String activationLink;

    @SerializedName("activationLinkDate")
    @Expose
    private LocalDate activationLinkDate;


    public User(Long id, String firstName, String lastName, Address address, String phoneNumber, Account account,
                String picturePath, String lastPasswordResetDate, String activationLink, LocalDate activationLinkDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.picturePath = picturePath;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.activationLink = activationLink;
        this.activationLinkDate = activationLinkDate;
    }

    public User() {}


    protected User(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        firstName = in.readString();
        lastName = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        phoneNumber = in.readString();
        account = in.readParcelable(Account.class.getClassLoader());
        picturePath = in.readString();
        activationLink = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(String lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public String getActivationLink() {
        return activationLink;
    }

    public void setActivationLink(String activationLink) {
        this.activationLink = activationLink;
    }

    public LocalDate getActivationLinkDate() {
        return activationLinkDate;
    }

    public void setActivationLinkDate(LocalDate activationLinkDate) {
        this.activationLinkDate = activationLinkDate;
    }


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
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeParcelable(address, flags);
        dest.writeString(phoneNumber);
        dest.writeParcelable(account, flags);
        dest.writeString(picturePath);
        dest.writeString(activationLink);
    }
}

