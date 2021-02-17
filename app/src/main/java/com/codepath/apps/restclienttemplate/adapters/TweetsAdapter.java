package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.PatternEditableBuilder;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.activities.TweetDetailActivity;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    // pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // for each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data at position
        Tweet tweet = tweets.get(position);
        // bind the tweet with view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements in the existing refeneced collection of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(ArrayList<Tweet> tweets) {
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    // define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfileImage;
        TextView tvBody, tvAge;
        TextView tvScreenName, tvName;
        ImageButton ibReply, ibRetweet, ibLike, ibShare;
        RelativeLayout rlTweetRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById(R.id.tvName);// FirstName LastName
            tvScreenName = itemView.findViewById(R.id.tvScreenName); // @aName
            tvAge  = itemView.findViewById(R.id.tvAge);

            ibReply = itemView.findViewById(R.id.ibReply);
            ibRetweet = itemView.findViewById(R.id.ibRetweet);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibShare = itemView.findViewById(R.id.ibShare);

            rlTweetRow = itemView.findViewById(R.id.rlTweetRow);
        }

        public void bind(@NotNull final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvName.setText(tweet.user.name);
            tvAge.setText(tweet.getRelativeTimeAgo(tweet.createdAt));
            tvScreenName.setText("@" + tweet.user.screenName);
            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .circleCrop()
//                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .into(ivProfileImage);
            new PatternEditableBuilder().
                    addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
                            new PatternEditableBuilder.SpannableClickedListener() {
                                @Override
                                public void onSpanClicked(String text) {
                                    // implementing routing to a new activity/view
                                    // specifically the user's profile that was clicked
                                    Toast.makeText(context, "Clicked username: " + text,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }).into(tvScreenName);
            new PatternEditableBuilder().
                    addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
                            new PatternEditableBuilder.SpannableClickedListener() {
                                @Override
                                public void onSpanClicked(String text) {
                                    // implementing routing to a new activity/view
                                    Toast.makeText(context, "Clicked username: " + text,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }).into(tvBody);


            // 1. Register click listener on the whole row-container
            rlTweetRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. Action to be taken is a navigation to a new activity on tap
                    Intent i = new Intent(context, TweetDetailActivity.class);
                    i.putExtra("tweet", Parcels.wrap(tweet));
                    context.startActivity(i);
                }
            });
        }

    }


}
