package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

    public String body;
    public String createdAt;
    public User user;
    public long id;
    public boolean isRetweeted, isFavorited, isPossiblySensitive;
    public int favoriteCount, retweetCount;

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


}
