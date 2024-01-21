package com.example.booking_team22.adapters;

import static android.content.Context.MODE_PRIVATE;

import static com.example.booking_team22.fragments.users.admin.ReportedCommentsFragment.getAccommodationComments;

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
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.fragments.users.admin.ReportedCommentsFragment;
import com.example.booking_team22.fragments.users.admin.ReportedUsersFragment;
import com.example.booking_team22.model.AccommodationComments;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.Notification;
import com.example.booking_team22.model.Status;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsAdapter extends ArrayAdapter<Comment> {
    private ArrayList<Comment> aComments;
    private SharedPreferences sp;
    private String userType;
    private Comment selectedComment;
    private String accessToken;

    private FragmentActivity context;



    public CommentsAdapter(FragmentActivity context, ArrayList<Comment> comments){
        super(context, R.layout.comment_card, comments);
        aComments = comments;
        this.context = context;
        sp = context.getApplicationContext().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");
        userType = sp.getString("userType","");
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
        selectedComment = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_card,
                    parent, false);
        }

        Button deleteAccommodationComm = convertView.findViewById(R.id.delete_accommodation_comment);
        Button reportAccommodationComm = convertView.findViewById(R.id.report_accommodation_comment);

        deleteAccommodationComm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete,0,0,0);
        reportAccommodationComm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_report_user,0,0,0);

        LinearLayout commentCard = convertView.findViewById(R.id.comment_card_item);
        TextView commentEmail = convertView.findViewById(R.id.comment_email);
        TextView commentText = convertView.findViewById(R.id.comment_text);
        TextView commentDate = convertView.findViewById(R.id.comment_date);
        Button acceptButton = convertView.findViewById(R.id.acceptComment);
        Button declineButton = convertView.findViewById(R.id.declineComment);
        Button reportButton=convertView.findViewById(R.id.reportComment);
        RatingBar ratingBar=convertView.findViewById(R.id.ratingAcc);

        if(selectedComment != null){
            commentEmail.setText(selectedComment.getGuest().getAccount().getUsername());
            commentText.setText(selectedComment.getText());
            commentDate.setText(selectedComment.getDate());
            ratingBar.setRating((float)selectedComment.getRating());
            if(!userType.equals("ROLE_ADMIN")){
                deleteAccommodationComm.setVisibility(View.INVISIBLE);
                reportAccommodationComm.setVisibility(View.INVISIBLE);
            }
            if(userType.equals("ROLE_HOST")){
                reportButton.setVisibility(View.INVISIBLE);
                deleteAccommodationComm.setVisibility(View.INVISIBLE);
                acceptButton.setVisibility(View.INVISIBLE);
                declineButton.setVisibility(View.INVISIBLE);
                reportAccommodationComm.setVisibility(View.VISIBLE);
            }
            if(userType.equals("ROLE_GUEST")){
                deleteAccommodationComm.setVisibility(View.VISIBLE);
                acceptButton.setVisibility(View.INVISIBLE);
                declineButton.setVisibility(View.INVISIBLE);
                reportAccommodationComm.setVisibility(View.INVISIBLE);
                reportButton.setVisibility(View.INVISIBLE);
            }
            if(userType.equals("ROLE_ADMIN")){
                deleteAccommodationComm.setVisibility(View.INVISIBLE);
                reportAccommodationComm.setVisibility(View.INVISIBLE);
                reportButton.setVisibility(View.INVISIBLE);
                acceptButton.setVisibility(View.VISIBLE);
                declineButton.setVisibility(View.VISIBLE);
                if(selectedComment.getStatus().equals(Status.PENDING)){
                    declineButton.setVisibility(View.INVISIBLE);
                }
            }
            commentCard.setOnClickListener(v -> {

            });


            deleteAccommodationComm.setOnClickListener(v -> {
                deleteComment();
            });

            reportAccommodationComm.setOnClickListener(v -> {
                reportComment();
            });

            acceptButton.setOnClickListener(v->{
                acceptComment(position);
            });

            declineButton.setOnClickListener(v->{
                declineComment(position);
            });
        }
        return convertView;
    }

    public void acceptComment(int position){
        Comment comment = aComments.get(position);
        if(isInAccommodationComments(comment.getId())){
            Call<Comment> callComment = ClientUtils.commentService.acceptAccommodationComment("Bearer " + accessToken, comment.getId(), comment);
            callComment.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.code() == 200) {
                        Log.d("COMMENTS", "Meesage recieved");
                        System.out.println(response.body());

                    } else {
                        Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                }
            });
        }
        else {
            Call<Comment> callComment = ClientUtils.commentService.acceptHostComment("Bearer " + accessToken, comment.getId(), comment);
            callComment.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.code() == 200) {
                        Log.d("COMMENTS", "Meesage recieved");
                        System.out.println(response.body());

                    } else {
                        Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                }
            });
        }
    };

    public boolean isInAccommodationComments(Long id){
        ArrayList<AccommodationComments> accommodationComments1 = getAccommodationComments();
        for(AccommodationComments aComment:accommodationComments1){
            Log.d(String.valueOf(aComment.getId()),String.valueOf(id));
            if(aComment.getId().equals(id)){
                return true;
            }
        }
        return false;
    };

    public void declineComment(int position){
        Comment comment = aComments.get(position);
        if(isInAccommodationComments(comment.getId())){
            Call<Comment> callComment = ClientUtils.commentService.declineAccommodationComment("Bearer " + accessToken, comment.getId(), comment);
            callComment.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.code() == 200) {
                        Log.d("COMMENTS", "Meesage recieved");
                        System.out.println(response.body());

                    } else {
                        Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                }
            });
        }
        else {
            Call<Comment> callComment = ClientUtils.commentService.declineHostComment("Bearer " + accessToken, comment.getId(), comment);
            callComment.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.code() == 200) {
                        Log.d("COMMENTS", "Meesage recieved");
                        System.out.println(response.body());

                    } else {
                        Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                }
            });
        }
    };


    public void reportComment() {
        Call<Comment> callComment = ClientUtils.commentService.reportComment("Bearer " + accessToken, Status.REPORTED, selectedComment.getId());
        callComment.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    System.out.println(response.body());
                    selectedComment = response.body();
                    Toast.makeText(context,"Successfully reported!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }


    public void deleteComment() {
        Call<Comment> callComment = ClientUtils.commentService.deleteComment("Bearer " + accessToken, selectedComment.getId());
        callComment.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    System.out.println(response.body());
                    selectedComment = response.body();
                    Toast.makeText(context,"Successfully deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }
}
