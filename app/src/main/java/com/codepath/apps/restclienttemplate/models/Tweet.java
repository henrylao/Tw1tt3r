package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public User user;
    public long id;
    public boolean isRetweeted, isFavorited, isPossiblySensitive;
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

    // TODO refer to: https://github.com/ZacharyST/Twitter_AdjustTimeCorrectly/blob/master/AdjustTimeToLocalTime.py
    public String getAge() {
//// Custom date format
//        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
//
//        Date d1 = null;
//        Date d2 = null;
//        try {
//            d1 = format.parse(dateStart);
//            d2 = format.parse(dateStop);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//// Get msec from each, and subtract.
//        long diff = d2.getTime() - d1.getTime();
//        long diffSeconds = diff / 1000;
//        long diffMinutes = diff / (60 * 1000);
//        long diffHours = diff / (60 * 60 * 1000);
//        System.out.println("Time in seconds: " + diffSeconds + " seconds.");
//        System.out.println("Time in minutes: " + diffMinutes + " minutes.");
//        System.out.println("Time in hours: " + diffHours + " hours.");
//
//
//
//
//        this.createdAt
        return "";
    }

}
