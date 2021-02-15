package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public String screenName;
    public String profileImageUrl;
    public long id;
    public String description;
    public String location;
    public int friendsCount, followersCount;
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
        return user;
    }
}


