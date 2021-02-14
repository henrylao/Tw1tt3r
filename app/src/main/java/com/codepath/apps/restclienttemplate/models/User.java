package com.codepath.apps.restclienttemplate.models;// models/User.java

import androidx.room.ColumnInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    @ColumnInfo
    String name;

    // normally this field would be annotated @PrimaryKey because this is an embedded object
    // it is not needed
    @ColumnInfo
    Long twitter_id;

    // Parse model from JSON
    public static User parseJSON(JSONObject tweetJson) throws JSONException {

        User user = new User();
        Long twitter_id = tweetJson.getLong("id");
        String name = tweetJson.getString("name");
        return user;
    }


}

