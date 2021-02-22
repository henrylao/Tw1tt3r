package com.codepath.apps.restclienttemplate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeTweetActivity extends AppCompatActivity {
    public static final int MAX_TWEET_LENGTH = 140;
    public static final String TAG = "ComposeTweetActivity";
    EditText etComposeTweet;
    TextInputLayout tilComposeTweet;
    Button btnCancel;
    Button btnTweet;
    TwitterClient client;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        // Setup REST client
        client = TwitterApplication.getRestClient(this);
        // Assign XML values to class variables
        tilComposeTweet = findViewById(R.id.tilComposeTweet);
        etComposeTweet = findViewById(R.id.etComposeTweet);
        btnCancel = findViewById(R.id.btnCancel);
        btnTweet = findViewById(R.id.btnTweet);

//        final String tweetContent = etComposeTweet.getText().toString();
//        if (tweetContent.length() > MAX_TWEET_LENGTH){
//            tilComposeTweet.setCounterTextColor(ColorStateList.valueOf(R.color.design_default_color_error));
//        }
        // Set click listeners for buttons
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                // no entry
                final String tweetContent = etComposeTweet.getText().toString();
                if (tweetContent.isEmpty()) {
                    // Twitter: make tweet button less visible
                    Toast.makeText(ComposeTweetActivity.this, "Composed!", Toast.LENGTH_SHORT).show(); // Codepath: notify by toast
                    return;
                }
                // entry too long
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    // Twitter: make tweet button less visible
                    // show how many characters over in red
                    Toast.makeText(ComposeTweetActivity.this, "Message is too long!", Toast.LENGTH_SHORT).show(); // Codepath
//                    tilComposeTweet.setErrorTextColor(ColorStateList.valueOf(R.color.design_default_color_error));
                }
                // Make an API call to Twitter to publish the new Tweet
                client.postTweet(
                        new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                Log.i(TAG, "onSuccess -->  postTweet");
                                try {
                                    Tweet tweetPosted = Tweet.fromJson(json.jsonObject);
                                    Log.i(TAG, "Tweet Posted: " + tweetContent);
                                    Intent intendToReturnToTimeline = new Intent();
                                    // package data to be returned
                                    intendToReturnToTimeline.putExtra("tweet", Parcels.wrap(tweetPosted));
                                    // set result code and pass bundled data for response
                                    setResult(RESULT_OK, intendToReturnToTimeline);
                                    // close out the activity and return to parent
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.e(TAG, "onFailure -->  postTweet", throwable);
                            }
                        },
                        tweetContent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etComposeTweet.getText().toString();
                //
                if (tweetContent.isEmpty()) {
                    Toast.makeText(ComposeTweetActivity.this, "Canceled!", Toast.LENGTH_SHORT).show();
                }
                //
                else {
                }
                // Make an API call to Twitter to publish the new Tweet


            }
        });
    }
}