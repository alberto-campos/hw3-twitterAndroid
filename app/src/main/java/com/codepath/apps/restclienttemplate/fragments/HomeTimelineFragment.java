package com.codepath.apps.restclienttemplate.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.activities.ComposeActivity;
import com.codepath.apps.restclienttemplate.activities.TimelineActivity;
import com.codepath.apps.restclienttemplate.models.Profile;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    private long maxId= 1;
    private static final int MAXTWEETS = 200;
    public static final int COUNT = 50;
    private static int retrievedTweets = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getTwitterClient();
        populateTimeline();




    }

    @Override
    public void customLoadMoreTweets() {
        if (retrievedTweets < MAXTWEETS)
        {
            populateTimeline();
        }
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {

            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //  aTweets.clear();
                 // Get maxId from JSON response
                ArrayList<Tweet> myTweets = Tweet.fromJSONArray(json);
                maxId = Tweet.getMaxId();
                addAll(myTweets);
                // Get maximum number of tweets
                if (myTweets.size() < COUNT) {
                    retrievedTweets = MAXTWEETS;
                }
                else
                {
                    retrievedTweets = myTweets.size();
                }

            }

            // Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR: ", errorResponse.toString());
            }
        }, maxId);
    } // end populateTimeline




//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case (R.id.miCompose):
//                 launchComposeView();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.login, menu);
//       // getMenuInflater().inflate(R.menu.login, menu);
//
//    }


}
