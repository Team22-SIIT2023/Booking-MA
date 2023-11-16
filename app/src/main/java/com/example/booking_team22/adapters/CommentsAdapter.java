package com.example.booking_team22.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

    public CommentsAdapter(FragmentActivity context, ArrayList<Comment> comments){
        super(context, R.layout.comment_card, comments);
        aComments = comments;
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
        LinearLayout notificationCard = convertView.findViewById(R.id.comment_card_item);
        TextView commentEmail = convertView.findViewById(R.id.comment_email);
        TextView commentText = convertView.findViewById(R.id.comment_text);
        TextView commentDate = convertView.findViewById(R.id.comment_date);

        if(comment != null){
            commentEmail.setText(comment.getEmail());
            commentText.setText(comment.getCommentText());
            commentDate.setText(comment.getDate());
            notificationCard.setOnClickListener(v -> {

            });
        }
        return convertView;
    }
}
