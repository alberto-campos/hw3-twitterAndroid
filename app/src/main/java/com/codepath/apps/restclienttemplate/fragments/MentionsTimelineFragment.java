package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    private long maxId= 1;
    private static final int MAXTWEETS = 200;
    public static final int COUNT = 50;
    private static int retrievedTweets = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getTwitterClient();
        populateMentionsTimeline();
    }


    private void populateMentionsTimeline() {
        client.getMentionsTimeline(new JsonHttpResponseHandler() {

            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

              //  addAll(Tweet.fromJSONArray(json));
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
        });
    } // end populateMentionsTimeline

    @Override
    public void customLoadMoreTweets() {
        if (retrievedTweets < MAXTWEETS) {
            populateMentionsTimeline();
        }
    }
}
