package com.codepath.apps.restclienttemplate.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class HomeTimelineFragment extends TweetsListFragment {

    private static final int REQUEST_CODE = 55;
    private TwitterClient client;
    private Profile usrProf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getTwitterClient();
        populateTimeline();
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {

            String a = "hnola";

            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //  aTweets.clear();
                addAll(Tweet.fromJSONArray(json));
                // aTweets.notifyDataSetChanged();
            }

            // Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR: ", errorResponse.toString());
            }
        });
    } // end populateTimeline


    private void onComposeTweet(final String message) {
        client.composeTweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("SUCCESS: ", json.toString());
                displayNewTweets(message);
                //super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR: ", errorResponse.toString());
                // super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        }, message);
    }


    public void displayNewTweets(String msg) {
//        aTweets.clear();
//        aTweets.add(Tweet.fromMsg(msg));
//        aTweets.notifyDataSetChanged();
//        lvTweets.notifyAll();

        //  populateTimeline();
    }


    private void populateProfile() {
        client.getMyProfile(new JsonHttpResponseHandler() {

            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                //super.onSuccess(statusCode, headers, json);

                Profile p = new Profile(json);
                usrProf = p;

//                editor.putString("username", p.getName());
//                editor.putString("screen_name", p.getScreenName());
//                editor.putString("profile_image_url", p.getProfileImageUrl());
//                editor.putLong("id", p.getUid());

            }

            // Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR: ", errorResponse.toString());
            }
        });
    } // end populateProfile


    private void launchComposeView() {
        Intent i = new Intent(this.getContext(), ComposeActivity.class);

        i.putExtra("code", REQUEST_CODE);
        i.putExtra("username", usrProf.getName());
        i.putExtra("screenname", usrProf.getScreenName());
        i.putExtra("url", usrProf.getProfileImageUrl());

        startActivityForResult(i, REQUEST_CODE);
    }

}
