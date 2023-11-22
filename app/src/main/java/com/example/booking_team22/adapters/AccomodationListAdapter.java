package com.example.booking_team22.adapters;


import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
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

import com.example.booking_team22.R;
import com.example.booking_team22.activities.HomeActivity;
import com.example.booking_team22.fragments.FragmentTransition;
import com.example.booking_team22.fragments.accomodation.AccommodationDetailFragment;
import com.example.booking_team22.model.Accomodation;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AccomodationListAdapter extends ArrayAdapter<Accomodation> {
    private ArrayList<Accomodation> aAccomodation;
    private FragmentActivity context;
    public AccomodationListAdapter(FragmentActivity context, ArrayList<Accomodation> products){
        super(context, R.layout.accomodation_card, products);
        aAccomodation = products;
        this.context=context;


    }
//    private static WeakReference<AppCompatActivity> mActivityReference;
//
//    public static void setActivityReference(AppCompatActivity activity) {
//        mActivityReference = new WeakReference<>(activity);
//    }


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

//ovo je za pravi detail, a ispod je samo proba za edit
        detailButton.setOnClickListener(v -> {
              FragmentTransition.to(AccommodationDetailFragment.newInstance(), context, true, R.id.fragment_nav_content_main);
//            AccommodationDetailFragment yourFragment = new AccommodationDetailFragment();
//            FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_nav_content_main, yourFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
        });
//        detailButton.setOnClickListener(v ->{
//            LayoutInflater.from(context);
//            View customLayout= LayoutInflater.from(getContext()).inflate(R.layout.filter_dialog,null);
//            AlertDialog.Builder builder=new AlertDialog.Builder(context);
//            builder.setView(customLayout);
//            AlertDialog dialog=builder.create();
//            dialog.show();
//        });

        if(accomodation != null){
            imageView.setImageResource(accomodation.getImage());
            productTitle.setText(accomodation.getTitle());
            productDescription.setText(accomodation.getDescription());
            acceptAccommodation.setVisibility(accomodation.isButtonVisible() ? View.VISIBLE : View.INVISIBLE);
            declineAccommodation.setVisibility(accomodation.isButtonVisible() ? View.VISIBLE : View.INVISIBLE);
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
