package com.codepath.apps.restclienttemplate.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.adapters.TweetsAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetDAO;
import com.codepath.apps.restclienttemplate.models.TweetWithUser;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    public final int REQUEST_CODE = 20;
    public static final String TAG = "TimeLineActivity";

    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeContainer;
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
//    TweetDAO tweetDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
//        tweetDAO = ((TwitterApplication) getApplicationContext()).getMyDatabase().tweetDAO();

        client = TwitterApplication.getRestClient(this);
        // setup swipe to refresh feature
        swipeContainer = findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "Refresh called -> fetching new data!");
                populateHomeTimeLine();
            }
        });
        // find the recycler view
        rvTweets = findViewById(R.id.rvTweets);

        // initialize the list of tweets and adapter
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //rv setup: layout manager and the adapter
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreData();

                Log.i(TAG, "onLoadMore: " + page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);

        // query for existing tweets in the DB
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                Log.i(TAG, "Getting data from database");
//                List<TweetWithUser> tweetWithUsers = tweetDAO.getRecentTweets();
//                List<Tweet> tweetsFromDB = TweetWithUser.getTweetList(tweetWithUsers);
//                adapter.clear();
//                adapter.addAll((ArrayList<Tweet>) tweetsFromDB);
//            }
//        });
        populateHomeTimeLine();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Broken check by menu ID variables
//        int menuID = item.getItemId();
//        int menuGroupID = item.getGroupId();
//        String menuTitleKnown = "compose";
//        String menuTitleVar = (String) item.getTitle();
//        int known = R.menu.compose;
        // TODO currently broken: current solution uses check by title
        if (item.getItemId() == R.menu.compose){
            Log.i(TAG, "onOptionsItemSelected --> checkedByID --> tapped");
        }
        if (item.getTitle().equals("compose")) {
            // compose icon was tapped
            // navigate to compose activity
            Toast.makeText(this, "Compose", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "onOptionsItemSelected --> checkedByTitle --> tapped");
            Intent intendToCompose = new Intent(this, ComposeTweetActivity.class);
            startActivityForResult(intendToCompose, REQUEST_CODE);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            // get data from intent
            Tweet tweet= Parcels.unwrap(data.getParcelableExtra("tweet"));
            // update rv with the newly composed tweet
            // modify collection of tweets
            tweets.add(0, tweet);
            // update adapter
            adapter.notifyItemInserted(0);
            rvTweets.smoothScrollToPosition(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadMoreData() {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        client.getNextPageOfTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.i(TAG, "loadMoreData --> onSuccess" + json.toString());
                Log.i(TAG, "loadMoreData last id: " + tweets.get(tweets.size() - 1).id);
                Log.i(TAG, tweets.get(tweets.size() - 1).toString());
                //  --> Deserialize and construct new model objects from the API response
                JSONArray jsonArray = json.jsonArray;
                try {
                    ArrayList<Tweet> tweets = Tweet.fromJsonArray(jsonArray);
                    //  --> Append the new data objects to the existing set of items inside the array of items
                    //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
                    adapter.addAll(tweets);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "loadMoreData --> onFailure", throwable);
            }
        }, tweets.get(tweets.size() - 1).id);
    }

    private void populateHomeTimeLine() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess" + json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                    final List<Tweet> tweetsFromNetwork = Tweet.fromJsonArray(jsonArray);
                    adapter.clear();
                    adapter.addAll((ArrayList<Tweet>) tweetsFromNetwork);
                    swipeContainer.setRefreshing(false);
//                    AsyncTask.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.i(TAG, "Saving data into cache database");
//                            // insert users first because of foreign key access
//                            List<User> usersFromNetwork = User.fromJsonTweetArray(tweetsFromNetwork);
//                            tweetDAO.insertModel(usersFromNetwork.toArray(new User[0]));
//                            // insert tweets after
//                            tweetDAO.insertModel(tweetsFromNetwork.toArray(new Tweet[0]));
//                        }
//                    });
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Json exception", e);
                    // e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "onFailure" + response, throwable);
            }
        });
    }
}