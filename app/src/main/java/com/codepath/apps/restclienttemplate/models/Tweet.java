package com.codepath.apps.restclienttemplate.models;

import java.util.regex.Pattern;

import android.text.format.DateUtils;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Parcel
public class Tweet {
    public static final String TAG = "Tweet";
//    @PrimaryKey
//    @ColumnInfo
    public long id;
//    @ColumnInfo
    public String body;
//    @ColumnInfo
    public String createdAt;
//    @Ignore
    public User user;
//    @ColumnInfo
    public long userId;
//    @ColumnInfo
    public boolean isRetweeted, isFavorited, isPossiblySensitive;
//    @ColumnInfo
    public int favoriteCount, // like count
            retweetCount;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {

        Tweet tweet = new Tweet();
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.isRetweeted = jsonObject.getBoolean("retweeted");
        tweet.isFavorited = jsonObject.getBoolean("favorited");
//        tweet.isPossiblySensitive = jsonObject.getBoolean("possibly_sensitive"); // cant extract for some reason
        tweet.favoriteCount = jsonObject.getInt("favorite_count");
        tweet.retweetCount = jsonObject.getInt("retweet_count");
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");

        return tweet;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return (ArrayList<Tweet>) tweets;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("id:" + id)
                .append("body:" + body)
                .append("createdAt:" + createdAt)
                .append("user:" + user.name)
                .toString();
    }

    /**
     * @param rawJsonDate
     * @return
     */
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        String rv = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            // if date is greater than 1 week then return the actual date of event
            // if (){}
            // else {
            if (relativeDate.compareTo("Yesterday") == 0) {
                rv = "1d";
            } else {
                String[] tokens = relativeDate.split(" ");
                rv = tokens[0] + tokens[1].substring(0, 1);
            }

//            Log.i(TAG, "getRelativeTimeAgo success" + relativeDate);
            // }
        } catch (ParseException e) {
            Log.i(TAG, "getRelativeTimeAgo failure: " + relativeDate, e);
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.i(TAG, "getRelativeTimeAgo failure: " + relativeDate, e);
//            Log.i(TAG, "getRelativeTimeAgo failure", e);
            e.printStackTrace();
        }
        return rv;
//        return relativeDate;
    }

    // TODO: create method for getting the datetime ==> Ex. 1:09 PM | 2/13/21


}
