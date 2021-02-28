package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class User {
    @PrimaryKey
    @ColumnInfo
    public long id;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public String screenName;
    @ColumnInfo
    public String profileImageUrl;
    @ColumnInfo
    public String description;
    @ColumnInfo
    public String location;
    @ColumnInfo
    public int friendsCount, followersCount;
    @ColumnInfo
    public boolean isVerified;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.id = jsonObject.getLong("id");
        user.friendsCount = jsonObject.getInt("friends_count");
        user.followersCount = jsonObject.getInt("followers_count");
        user.location = jsonObject.getString("location");
        user.description = jsonObject.getString("description");
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        user.isVerified = jsonObject.getBoolean("verified");
        return user;
    }

    public static List<User> fromJsonTweetArray(List<Tweet> tweetsFromNetwork) {
        List<User> users = new ArrayList<>();
        for (Tweet t: tweetsFromNetwork){
            users.add(t.user);
        }
        return users;
    }
}


