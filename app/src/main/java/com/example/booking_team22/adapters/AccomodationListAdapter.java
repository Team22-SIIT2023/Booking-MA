package com.example.booking_team22.adapters;


import static android.app.PendingIntent.getActivity;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.booking_team22.R;
import com.example.booking_team22.activities.AccommodationDetailsScreenActivity;
import com.example.booking_team22.model.Accomodation;

import java.util.ArrayList;

public class AccomodationListAdapter extends ArrayAdapter<Accomodation> {
    private ArrayList<Accomodation> aAccomodation;
    private FragmentActivity context;

    public AccomodationListAdapter(FragmentActivity context, ArrayList<Accomodation> products){
        super(context, R.layout.accomodation_card, products);
        aAccomodation = products;
        this.context=context;

    }

    @Override
    public int getCount() {
        return aAccomodation.size();
    }

    @Nullable
    @Override
    public Accomodation getItem(int position) {
        return aAccomodation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Accomodation accomodation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accomodation_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
        ImageView imageView = convertView.findViewById(R.id.product_image);
        TextView productTitle = convertView.findViewById(R.id.product_title);
        TextView productDescription = convertView.findViewById(R.id.product_description);
        Button detailButton=convertView.findViewById(R.id.viewDetailButton);

        if(accomodation != null){
            imageView.setImageResource(accomodation.getImage());
            productTitle.setText(accomodation.getTitle());
            productDescription.setText(accomodation.getDescription());
            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + accomodation.getTitle() + ", id: " +
                        accomodation.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + accomodation.getTitle()  +
                        ", id: " + accomodation.getId().toString(), Toast.LENGTH_SHORT).show();
            });
            detailButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, AccommodationDetailsScreenActivity.class);
                context.startActivity(intent);
            });
        }

        return convertView;
    }
}
