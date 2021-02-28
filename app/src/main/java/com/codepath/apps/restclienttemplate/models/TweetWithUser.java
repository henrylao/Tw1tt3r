package com.codepath.apps.restclienttemplate.models;

import androidx.room.Embedded;

import java.util.ArrayList;
import java.util.List;

public class TweetWithUser {
    // @Embedded denotes flattening of properties of User object into the object for persisting information
//    @Embedded
//    User user;
//
//    @Embedded(prefix = "tweet_")
//    Tweet tweet;
//
//    public static List<Tweet> getTweetList(List<TweetWithUser> tweetWithUsers){
//        List<Tweet> tweets = new ArrayList<>();
//        for (TweetWithUser twu : tweetWithUsers){
//            Tweet t = twu.tweet;
//            t.user = twu.user;
//            tweets.add(t);
//        }
//        return tweets;
//    }

}
