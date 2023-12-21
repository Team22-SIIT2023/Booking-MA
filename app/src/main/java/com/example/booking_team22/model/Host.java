package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDate;

public class Host extends User implements Parcelable, Serializable{

    public Host(Long id, String firstName, String lastName, Address address, String phoneNumber, Account account, String picturePath, String lastPasswordResetDate, String activationLink, LocalDate activationLinkDate) {
        super(id, firstName, lastName, address, phoneNumber, account, picturePath, lastPasswordResetDate, activationLink, activationLinkDate);
    }

    public Host() {
    }

    public Host(Parcel in) {
        super(in);
    }
}
