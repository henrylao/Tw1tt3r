package com.codepath.apps.restclienttemplate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;

public class ComposeTweetActivity extends AppCompatActivity {
    public static final int MAX_TWEET_LENGTH = 140;
    EditText etComposeTweet;
    Button btnCancel;
    Button btnTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        // Assign XML values to class variables
        etComposeTweet = findViewById(R.id.etComposeTweet);
        btnCancel = findViewById(R.id.btnCancel);
        btnTweet = findViewById(R.id.btnTweet);
        // Set click listeners for buttons
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etComposeTweet.getText().toString();
                // no entry
                if (tweetContent.isEmpty()){
                    // Twitter: make tweet button less visible
                    Toast.makeText(ComposeTweetActivity.this, "Composed!", Toast.LENGTH_SHORT).show(); // Codepath: notify by toast
                    return;
                }
                // entry too long
                if (tweetContent.length() > MAX_TWEET_LENGTH){
                    // Twitter: make tweet button less visible
                    // show how many characters over in red
                    Toast.makeText(ComposeTweetActivity.this, "Message is too long!", Toast.LENGTH_SHORT).show(); // Codepath
                }
                // Make an API call to Twitter to publish the new Tweet
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etComposeTweet.getText().toString();
                //
                if (tweetContent.isEmpty()){
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