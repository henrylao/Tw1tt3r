// models/Tweet.java
package com.codepath.apps.restclienttemplate.models;

//import androidx.room.ColumnInfo;
//import androidx.room.Embedded;
//
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//@Entity
public class Tweet {
    public User user;
    public String body;
    public String createdAt;
    // ...existing code from above...

    // Add a constructor that creates an object from the JSON response
    public Tweet(JSONObject object) throws JSONException {
        this.user = User.parseJSON(object.getJSONObject("user"));
//            this.userHandle = object.getString("user_username");
//            this.timestamp = object.getString("timestamp");
        this.createdAt = object.getString("created_at");
        this.body = object.getString("text");
    }


    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) throws JSONException {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            tweetJson = jsonArray.getJSONObject(i);

            Tweet tweet = new Tweet(tweetJson);
            tweets.add(tweet);
        }

        return tweets;
    }

//    // Define database columns and associated fields
//    @PrimaryKey
//    @ColumnInfo
//    public Long id;
//    @ColumnInfo
//    public String userHandle;
//    @ColumnInfo
//    public String timestamp;
//    @ColumnInfo
//    public String body;
//
//    // Use @Embedded to keep the column entries as part of the same table while still
//    // keeping the logical separation between the two objects.
//    @Embedded
//    public User user;
}

