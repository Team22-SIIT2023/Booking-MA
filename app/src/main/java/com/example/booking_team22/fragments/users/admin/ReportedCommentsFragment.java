package com.example.booking_team22.fragments.users.admin;

import static android.content.Context.MODE_PRIVATE;
import static com.example.booking_team22.clients.ClientUtils.commentService;
import static com.example.booking_team22.clients.ClientUtils.userService;

import android.content.SharedPreferences;
import android.database.Observable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.booking_team22.R;
import com.example.booking_team22.adapters.AccomodationListAdapter;
import com.example.booking_team22.adapters.CommentsAdapter;
import com.example.booking_team22.clients.ClientUtils;
import com.example.booking_team22.databinding.FragmentAccommodationDetailBinding;
import com.example.booking_team22.databinding.FragmentReportedCommentBinding;
import com.example.booking_team22.model.AccommodationComments;
import com.example.booking_team22.model.Accomodation;
import com.example.booking_team22.model.Comment;
import com.example.booking_team22.model.HostComments;
import com.example.booking_team22.model.Status;
import com.example.booking_team22.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportedCommentsFragment extends ListFragment {
    private SharedPreferences sp;
    private String userType;
    private String accessToken;

    FragmentReportedCommentBinding binding;

    private ArrayList<Comment> comments = new ArrayList<Comment>();

    private ArrayList<AccommodationComments> commentsForFirstCall = new ArrayList<AccommodationComments>();

    private ArrayList<HostComments> commentsForSecondCall = new ArrayList<HostComments>();

    private ArrayList<AccommodationComments> commentsForThirdCall = new ArrayList<AccommodationComments>();

    private ArrayList<HostComments> commentsForFourthCall = new ArrayList<HostComments>();

    private static ArrayList<AccommodationComments> accommodationComments = new ArrayList<>();

    private ArrayList<HostComments> hostComments = new ArrayList<>();

    private User user;
    CommentsAdapter adapter;
    Accomodation accommodation;

    public ReportedCommentsFragment() {
        // Required empty public constructor
    }

    public static ReportedCommentsFragment newInstance(Accomodation accommodation) {
        ReportedCommentsFragment fragment = new ReportedCommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable("accommodation", accommodation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.accommodation = args.getParcelable("accommodation");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReportedCommentBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        sp = getActivity().getSharedPreferences("mySharedPrefs",MODE_PRIVATE);
        accessToken = sp.getString("accessToken", "");

        userType = sp.getString("userType","");
        long id = sp.getLong("userId",0L);

        Call<User> callUser = userService.getUser(id);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Log.d("USER", "Message received");
                    user = response.body();
                } else {
                    Log.d("USER_REQUEST", "Message received: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("USER_REQUEST", "Error: " + t.getMessage(), t);
            }
        });


        if (userType.equals("ROLE_GUEST") || userType.equals("ROLE_HOST")) {
            Call<ArrayList<Comment>> callComment = ClientUtils.commentService.getHostComments("Bearer " + accessToken, accommodation.getHost().getId(), Status.ACTIVE.name());
            callComment.enqueue(new Callback<ArrayList<Comment>>() {
                @Override
                public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                    if (response.code() == 200) {
                        Log.d("COMMENTS", "Meesage recieved");
                        System.out.println(response.body());
                        comments = response.body();
                        adapter=new CommentsAdapter(getActivity(),comments);
                        binding.list.setAdapter(adapter);
//                        enableListScroll(binding.list);
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                    Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                }
            });

        }
        else {
            displayReportedAndPendingComments();
        }

//        return inflater.inflate(R.layout.fragment_reported_comment, container, false);

        return root;

    }

    public static ArrayList<AccommodationComments> getAccommodationComments(){
        return accommodationComments;
    };

    public ArrayList<HostComments> getHostComments(){
        return hostComments;
    };


    public void displayReportedAndPendingComments() {
        Call<ArrayList<AccommodationComments>> firstCall = ClientUtils.commentService.getAccommodationsComments("Bearer " + accessToken, "PENDING");
        firstCall.enqueue(new Callback<ArrayList<AccommodationComments>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationComments>> call, Response<ArrayList<AccommodationComments>> response) {
                if (response.code() == 200) {
                    Log.d("COMMENTS", "Meesage recieved");
                    System.out.println(response.body());
                    commentsForFirstCall = response.body();
                    accommodationComments.addAll(commentsForFirstCall);

                    Call<ArrayList<HostComments>> secondCall = ClientUtils.commentService.getHostsComments("Bearer " + accessToken,"PENDING");
                    secondCall.enqueue(new Callback<ArrayList<HostComments>>() {
                        @Override
                        public void onResponse(Call<ArrayList<HostComments>> call, Response<ArrayList<HostComments>> response) {
                            if (response.code() == 200) {
                                Log.d("COMMENTS", "Meesage recieved");
                                System.out.println(response.body());
                                commentsForSecondCall = response.body();
                                hostComments.addAll(commentsForSecondCall);

                                Call<ArrayList<AccommodationComments>> thirdCall = ClientUtils.commentService.getAccommodationsComments("Bearer " + accessToken, "REPORTED");
                                thirdCall.enqueue(new Callback<ArrayList<AccommodationComments>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<AccommodationComments>> call, Response<ArrayList<AccommodationComments>> response) {
                                        if (response.code() == 200) {
                                            Log.d("COMMENTS", "Meesage recieved");
                                            System.out.println(response.body());
                                            commentsForThirdCall = response.body();
                                            accommodationComments.addAll(commentsForThirdCall);

                                            Call<ArrayList<HostComments>> fourthCall = ClientUtils.commentService.getHostsComments("Bearer " + accessToken, "REPORTED");
                                            fourthCall.enqueue(new Callback<ArrayList<HostComments>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<HostComments>> call, Response<ArrayList<HostComments>> response) {
                                                    if (response.code() == 200) {
                                                        Log.d("COMMENTS", "Meesage recieved");
                                                        System.out.println(response.body());
                                                        commentsForFourthCall = response.body();
                                                        hostComments.addAll(commentsForFourthCall);

                                                        comments.addAll(commentsForFirstCall);
                                                        comments.addAll(commentsForSecondCall);
                                                        comments.addAll(commentsForThirdCall);
                                                        comments.addAll(commentsForFourthCall);

                                                        adapter=new CommentsAdapter(getActivity(),comments);
                                                        binding.list.setAdapter(adapter);
                                                        adapter.notifyDataSetChanged();

                                                    } else {
                                                        Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<ArrayList<HostComments>> call, Throwable t) {
                                                    Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                                                }
                                            });
                                        } else {
                                            Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ArrayList<AccommodationComments>> call, Throwable t) {
                                        Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                                    }
                                });
                            } else {
                                Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<HostComments>> call, Throwable t) {
                            Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
                        }
                    });

                } else {
                    Log.d("COMMENT LOS", "Meesage recieved: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AccommodationComments>> call, Throwable t) {
                Log.e("COMMENTS_REQUEST", "Error: " + t.getMessage(), t);
            }
        });
    }
}
