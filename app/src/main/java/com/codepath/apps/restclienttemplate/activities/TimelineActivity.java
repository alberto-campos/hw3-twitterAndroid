package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.adapters.TweetsArrayAdapter;
import com.codepath.apps.restclienttemplate.models.Profile;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 1344;
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

//    SharedPreferences appProfile = getApplication().getSharedPreferences("Profile", 0);
//    SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(getApplication());
//    SharedPreferences.Editor editor = appSettings.edit();

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
        populateProfile();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.miCompose):
                launchComposeView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchComposeView() {
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);

        i.putExtra("username", "xopmac");
        i.putExtra("code", REQUEST_CODE);
        startActivityForResult(i, REQUEST_CODE);
       // startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String message = data.getExtras().getString("message");
           // int code = data.getExtras().getInt("code", 0);
            // Toast the name to display temporarily on screen
            Toast.makeText(this, "New message: " + message, Toast.LENGTH_SHORT).show();
        }
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
                Log.d("SUCCESS: ", json.toString());
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


    private void populateProfile() {
        client.getMyProfile(new JsonHttpResponseHandler() {

            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                //super.onSuccess(statusCode, headers, json);

               // Profile p = new Profile(json);
                Profile p = new Profile(json);
               // p.fromJSON((json));
                Log.d("DEBUG: ", json.toString());
                Log.d("DEBUG", p.getName() +" HOLA AMIGOS " + p.getScreenName());
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

}
