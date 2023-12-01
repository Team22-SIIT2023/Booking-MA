package com.example.booking_team22.adapters;


import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.booking_team22.R;
import com.example.booking_team22.activities.HomeActivity;
import com.example.booking_team22.fragments.FragmentTransition;
import com.example.booking_team22.fragments.accomodation.AccommodationDetailFragment;
import com.example.booking_team22.model.Accomodation;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AccomodationListAdapter extends ArrayAdapter<Accomodation> {
    private ArrayList<Accomodation> aAccomodation;
    private SharedPreferences sp;
    private String userType;
    private FragmentActivity context;

    public AccomodationListAdapter(FragmentActivity context, ArrayList<Accomodation> products){
        super(context, R.layout.accomodation_card, products);
        aAccomodation = products;
        this.context=context;
        sp= context.getApplicationContext().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        userType=sp.getString("userType","");

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
        Button acceptAccommodation = convertView.findViewById(R.id.acceptAccommodation);
        Button declineAccommodation = convertView.findViewById(R.id.declineAccommodation);

        detailButton.setOnClickListener(v -> {
             NavController navController = Navigation.findNavController(context, R.id.fragment_nav_content_main);
             navController.navigate(R.id.nav_details);
        });

        if(accomodation != null){
            imageView.setImageResource(accomodation.getImage());
            productTitle.setText(accomodation.getTitle());
            productDescription.setText(accomodation.getDescription());
            if(!userType.equals("admin")){
                acceptAccommodation.setVisibility(View.GONE);
                declineAccommodation.setVisibility(View.GONE);
            }
            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + accomodation.getTitle() + ", id: " +
                        accomodation.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + accomodation.getTitle()  +
                        ", id: " + accomodation.getId().toString(), Toast.LENGTH_SHORT).show();
            });

        }

        return convertView;
    }
}
