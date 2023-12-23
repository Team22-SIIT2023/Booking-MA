package com.example.booking_team22.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

public class Guest extends User implements Parcelable, Serializable {
    //private Collection<Accomodation> favoriteAccommodations;

    public Guest(Long id, String firstName, String lastName, Address address, String phoneNumber, Account account, String picturePath, String lastPasswordResetDate, String activationLink, LocalDate activationLinkDate) {
        super(id, firstName, lastName, address, phoneNumber, account, picturePath, lastPasswordResetDate, activationLink, activationLinkDate);
        //this.favoriteAccommodations = favoriteAccommodations;
    }

//    public Guest(Collection<Accomodation> favoriteAccommodations) {
//       // this.favoriteAccommodations = favoriteAccommodations;
//    }

    public Guest(Parcel in) {
        super(in);
        //this.favoriteAccommodations = favoriteAccommodations;
    }
}
