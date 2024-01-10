package com.example.booking_team22.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.booking_team22.R;
import com.example.booking_team22.model.Amenity;

import java.util.ArrayList;


public class AmenityListAdapter extends ArrayAdapter<Amenity> {

    ArrayList<Amenity> aAmenities;
    public AmenityListAdapter(FragmentActivity context, ArrayList<Amenity> amenities){
        super(context, R.layout.activity_amenities_list_view, amenities);
        aAmenities = amenities;
    }

    @Override
    public int getCount() {
        return aAmenities.size();
    }

    @Nullable
    @Override
    public Amenity getItem(int position) {
        return aAmenities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Amenity amenity = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.amenity_card,
                    parent, false);
        }
        LinearLayout amenityCard = convertView.findViewById(R.id.amenity_card_item);
        TextView amenityName = convertView.findViewById(R.id.textViewAmenity);
        amenityName.setText(amenity.getAmenityName());
        //ImageView amenityImage = convertView.findViewById(R.id.imageIconAmenity);



        return convertView;
    }
}
