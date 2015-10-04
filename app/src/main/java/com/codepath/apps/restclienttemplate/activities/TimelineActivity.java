package com.codepath.apps.restclienttemplate.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.adapters.TweetsArrayAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        setupActionBar();

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        // Create the array List
        tweets = new ArrayList<>();
        // Construct the adapter from Dsource
        aTweets = new TweetsArrayAdapter(this, tweets);
        // Connect adapter to listView
        lvTweets.setAdapter(aTweets);

        client = TwitterApplication.getTwitterClient();
        populateTimeline();
    }

    private void setupActionBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Timeline");
        // actionBar.setIcon(R.drawable.twittericon); TODO: Fix, icon looks HUGE
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        actionBar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("SUCCESS: ",json.toString());
                // JSON
                // deserialize JSON
                // Create models and add them into the adapter
                // Display into ListView
                aTweets.addAll(Tweet.fromJSONArray(json));
            }

            // Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR: ", errorResponse.toString());
            }
        });
    } // end populateTimeline
}
