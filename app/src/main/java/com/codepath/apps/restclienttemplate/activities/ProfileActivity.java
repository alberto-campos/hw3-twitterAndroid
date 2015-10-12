package com.codepath.apps.restclienttemplate.activities;

import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ProfileActivity extends ActionBarActivity {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the screen name from activity that Launches this
        String screenName = getIntent().getStringExtra("screen_name");

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setIcon(R.drawable.twittericon);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getTwitterClient();

        if ( (screenName != "") && (screenName != null)) {

//            client.getUserHomeTimeline(screenName, new JsonHttpResponseHandler(){
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    super.onSuccess(statusCode, headers, response);
//                    user = User.fromJSON(response);
//                   getSupportActionBar().setTitle(user.getName());
//                    populateProfileHeader(user);
//
//                }
//            });

            client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    user = User.fromJSON(response);
                    //getSupportActionBar().setTitle(user.getName());
                    populateProfileHeader(user);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        } else {
            client.getMyProfile(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    // Return the current's user account info
                    getSupportActionBar().setTitle(user.getName());
                    populateProfileHeader(user);
                }
            });
        }
        if (savedInstanceState == null) {
            // Create the user timeline fragment
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            // Display user fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        TextView tvTweetCount = (TextView) findViewById(R.id.tvTweetsCount);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ImageView ivProfileBackground = (ImageView) findViewById(R.id.ivProfileBackground);

        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvTweetCount.setText(user.getStatusesCount() + " tweets");
        tvFollowers.setText(user.getFollowersCount() + " followers");
        tvFollowing.setText(user.getFriendsCount() + " following");
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
        Picasso.with(this).load(user.getProfileBannerUrl() + "/mobile_retina").into(ivProfileBackground);

    }

}
