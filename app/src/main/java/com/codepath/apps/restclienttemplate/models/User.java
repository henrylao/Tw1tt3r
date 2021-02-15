package com.codepath.apps.restclienttemplate.models;// models/User.java

import androidx.room.ColumnInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public String name;
    public String screenName;
    public String profileImageUrl;
    // normally this field would be annotated @PrimaryKey because this is an embedded object
    // it is not needed

    // Parse model from JSON
    public static User parseJSON(JSONObject jsonObject) throws JSONException {

        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName =  jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        return user;
    }


}

