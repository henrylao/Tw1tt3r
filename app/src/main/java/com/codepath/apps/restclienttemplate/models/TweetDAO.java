package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

//@Dao
public interface TweetDAO {
//    // Record finders
//    @Query("SELECT * FROM Tweet WHERE id = :tweetId")
//    Tweet byTweetId(Long tweetId);
//
//    @Query("SELECT Tweet.body AS tweet_body, Tweet.createdAt AS tweet_createdAt, Tweet.id AS tweet_id " +
//            "FROM Tweet INNER JOIN User ON Tweet.userId = User.id ORDER BY Tweet.createdAt DESC LIMIT 5")
//    List<TweetWithUser> getRecentTweets();
//
//    // Replace strategy is needed to ensure an update on the table row.  Otherwise the insertion will
//    // fail.
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertModel(Tweet... tweets);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertModel(User... users);
}

