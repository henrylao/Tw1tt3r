package com.codepath.apps.restclienttemplate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.PatternEditableBuilder;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.regex.Pattern;

public class TweetDetailActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    TextView tvName, tvBody, tvScreenName, tvRetweets, tvShares, tvLikes;
    ImageButton ibReply, ibRetweet, ibLike, ibShare;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        // image views
        ivProfileImage = findViewById(R.id.ivProfileImage);
        // textviews
        tvBody = findViewById(R.id.tvBody);
        tvName = findViewById(R.id.tvName);// FirstName LastName
        tvScreenName = findViewById(R.id.tvScreenName); // @aName
        tvRetweets = findViewById(R.id.tvRetweets);
        tvShares = findViewById(R.id.tvShares);
        tvLikes = findViewById(R.id.tvLikes);
        // image buttons
        ibReply = findViewById(R.id.ibReply);
        ibRetweet = findViewById(R.id.ibRetweet);
        ibLike = findViewById(R.id.ibLike);
        ibShare = findViewById(R.id.ibShare);
        // unwrap serialized tweet
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        // populate views
        // ==> textviews
        tvBody.setText(tweet.body);
        tvName.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvRetweets.setText(tweet.retweetCount + " Retweets");
        tvShares.setText("<?> Quote Tweets");
        tvLikes.setText(tweet.favoriteCount + " Likes");
        // ==> image views
        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .circleCrop()
//                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                .into(ivProfileImage);
        // TODO: use method for getting the datetime (1:09 PM | 2/13/21) to populate tvDatetime
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                // implementing routing to a new activity/view
                                // specifically the user's profile that was clicked
                                Toast.makeText(TweetDetailActivity.this, "Clicked username: " + text,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).into(tvScreenName);
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), Color.BLUE,
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                // implementing routing to a new activity/view
                                Toast.makeText(TweetDetailActivity.this, "Clicked username: " + text,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).into(tvBody);

    }
}