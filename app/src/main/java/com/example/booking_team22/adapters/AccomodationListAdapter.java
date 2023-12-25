package com.example.booking_team22.adapters;


import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.fragments.FragmentTransition;
import com.example.booking_team22.fragments.accomodation.AccommodationDetailFragment;
import com.example.booking_team22.model.AccommodationStatus;
import com.example.booking_team22.model.Accomodation;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        TextView productTitle = convertView.findViewById(R.id.product_title);
        TextView productDescription = convertView.findViewById(R.id.product_description);
        Button detailButton=convertView.findViewById(R.id.viewDetailButton);
        Button updateButton=convertView.findViewById(R.id.accommodationUpdate);
        Button acceptAccommodation = convertView.findViewById(R.id.acceptAccommodation);
        Button declineAccommodation = convertView.findViewById(R.id.declineAccommodation);




        if(accomodation != null){
            Call<List<String>> call = ClientUtils.accommodationService.getImages(accomodation.getId());
            ImageView imageView = convertView.findViewById(R.id.product_image);
            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if (response.isSuccessful()) {
                        List<String> images = response.body();
                        if (images != null && !images.isEmpty()) {
                            String imageString = images.get(0);
                            byte[] decodedBytes = Base64.decode(imageString, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                }
            });
            productTitle.setText(accomodation.getName());
            productDescription.setText(accomodation.getDescription());
            if(!userType.equals("ROLE_ADMIN")){
                acceptAccommodation.setVisibility(View.GONE);
                declineAccommodation.setVisibility(View.GONE);
            }
            if(!userType.equals("ROLE_HOST")){
                updateButton.setVisibility(View.GONE);
            }
            if(accomodation.getStatus()==AccommodationStatus.ACCEPTED){
                if(userType.equals("ROLE_ADMIN")){
                    acceptAccommodation.setVisibility(View.GONE);
                    declineAccommodation.setVisibility(View.GONE);
                }
            }



            detailButton.setOnClickListener(v->{
                Call<Accomodation> callDetails = ClientUtils.accommodationService.getById(accomodation.getId());
                callDetails.enqueue(new Callback<Accomodation>() {
                    @Override
                    public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                        if (response.code() == 200){
                            Log.d("UCITA PRVO","GET PRODUCT BY ID " + accomodation.getId());
                            Log.d("UCITA PRVO", response.body().toString());

                            Accomodation detailAccommodation = response.body();
                            Bundle args = new Bundle();
                            args.putParcelable("detailAccommodation", detailAccommodation);
                            NavController navController = Navigation.findNavController(context, R.id.fragment_nav_content_main);
                            navController.navigate(R.id.nav_details,args);
                        }else{
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Accomodation> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            });
            updateButton.setOnClickListener(v->{
                Call<Accomodation> callDetails = ClientUtils.accommodationService.getById(accomodation.getId());
                callDetails.enqueue(new Callback<Accomodation>() {
                    @Override
                    public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                        if (response.code() == 200){
                            Log.d("UCITA PRVO","GET PRODUCT BY ID " + accomodation.getId());
                            Log.d("UCITA PRVO", response.body().toString());

                            Accomodation updateAccommodation = response.body();
                            Bundle args = new Bundle();
                            args.putParcelable("updateAccommodation", updateAccommodation);
                            NavController navController = Navigation.findNavController(context, R.id.fragment_nav_content_main);
                            navController.navigate(R.id.nav_update,args);
                        }else{
                            Log.d("REZ","Update recieved: "+response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Accomodation> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            });
            acceptAccommodation.setOnClickListener(v->{
                Call<Accomodation> callAccept = ClientUtils.accommodationService.getById(accomodation.getId());
                callAccept.enqueue(new Callback<Accomodation>() {
                    @Override
                    public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                        if (response.code() == 200) {
                            Accomodation acceptingAccommodation = response.body();
                            Call<Accomodation> callAccept1 = ClientUtils.accommodationService.accept(acceptingAccommodation, acceptingAccommodation.getId());
                            callAccept1.enqueue(new Callback<Accomodation>() {
                                @Override
                                public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                                    if (response.code() == 200) {
                                    }
                                    else {
                                        Log.d("REZ", "Update recieved: " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Accomodation> call, Throwable t) {

                                }
                            });
                        } else {
                            Log.d("REZ", "Update recieved: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Accomodation> call, Throwable t) {

                    }
                });
            });
            declineAccommodation.setOnClickListener(v->{
                Call<Accomodation> callDecline = ClientUtils.accommodationService.getById(accomodation.getId());
                callDecline.enqueue(new Callback<Accomodation>() {
                    @Override
                    public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                        if (response.code() == 200) {
                            Accomodation decliningAccommodation = response.body();
                            Call<Accomodation> callDecline1 = ClientUtils.accommodationService.decline(decliningAccommodation, decliningAccommodation.getId());
                            callDecline1.enqueue(new Callback<Accomodation>() {
                                @Override
                                public void onResponse(Call<Accomodation> call, Response<Accomodation> response) {
                                    if (response.code() == 200) {
                                    }
                                    else {
                                        Log.d("REZ", "Update recieved: " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Accomodation> call, Throwable t) {

                                }
                            });
                        } else {
                            Log.d("REZ", "Update recieved: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Accomodation> call, Throwable t) {

                    }
                });
            });

        }

        return convertView;
    }
}
