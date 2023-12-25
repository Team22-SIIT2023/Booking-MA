package com.example.booking_team22.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.booking_team22.R;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Notification;

import java.util.ArrayList;

public class CommentsAdapter extends ArrayAdapter<Comment> {
    private ArrayList<Comment> aComments;
    private SharedPreferences sp;
    private String userType;

    public CommentsAdapter(FragmentActivity context, ArrayList<Comment> comments){
        super(context, R.layout.comment_card, comments);
        aComments = comments;
        sp= context.getApplicationContext().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        userType=sp.getString("userType","");
    }


    @Override
    public int getCount() {
        return aComments.size();
    }

    @Nullable
    @Override
    public Comment getItem(int position) {
        return aComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Comment comment = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_card,
                    parent, false);
        }
        LinearLayout commentCard = convertView.findViewById(R.id.comment_card_item);
        TextView commentEmail = convertView.findViewById(R.id.comment_email);
        TextView commentText = convertView.findViewById(R.id.comment_text);
        TextView commentDate = convertView.findViewById(R.id.comment_date);
        Button acceptButton = convertView.findViewById(R.id.acceptComment);
        Button declineButton = convertView.findViewById(R.id.declineComment);
        Button reportButton=convertView.findViewById(R.id.reportComment);
        RatingBar ratingBar=convertView.findViewById(R.id.ratingAcc);

        if(comment != null){
            commentEmail.setText(comment.getGuest().getAccount().getUsername());
            commentText.setText(comment.getText());
            commentDate.setText(comment.getDate());
            ratingBar.setRating((float)comment.getRating());
            if(!userType.equals("admin")){
                acceptButton.setVisibility(View.INVISIBLE);
                declineButton.setVisibility(View.INVISIBLE);
            }
            if(userType.equals("host")){
                reportButton.setVisibility(View.VISIBLE);
            }
            commentCard.setOnClickListener(v -> {

            });
        }
        return convertView;
    }
}
