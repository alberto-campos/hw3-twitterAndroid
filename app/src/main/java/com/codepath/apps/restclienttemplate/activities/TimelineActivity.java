package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.MentionsTimelineFragment;
import com.codepath.apps.restclienttemplate.models.Profile;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class TimelineActivity extends AppCompatActivity {


  //  private final int REQUEST_CODE = 1344;
  private static final int RESULT_OK = 1;
    private final int REQUEST_CODE = 1344;
    private Profile usrProf;

    private TwitterClient client;


    // private TweetsListFragment fragmentTweetsList;


//    SharedPreferences appProfile = getApplication().getSharedPreferences("Profile", 0);
   // SharedPreferences appSettings = PreferenceManager.getDefaultSharedPreferences(getApplication());
   // SharedPreferences.Editor editor = appSettings.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwitterApplication.getTwitterClient();
        populateProfile();

        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        tabStrip.setViewPager(vpPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.login, menu);
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
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
       // Intent i = new Intent(this.getContext(), ComposeActivity.class);
        Intent i = new Intent(getApplicationContext(), ComposeActivity.class);

        i.putExtra("code", REQUEST_CODE);
        i.putExtra("username", usrProf.getName());
        i.putExtra("screenname", usrProf.getScreenName());
        i.putExtra("url", usrProf.getProfileImageUrl());

        startActivityForResult(i, REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String message = data.getExtras().getString("message");
            onComposeTweet(message);
        }
    }

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



    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabItems[] = { "Home", "Mentions" };

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else
            {
              return new MentionsTimelineFragment();
            }
        }

        @Override
        public int getCount() {
            return tabItems.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabItems[position];
        }
    }


    public void onCompose(MenuItem item) {
        launchComposeView();
    }


    public void onProfileView(MenuItem item) {
        // Launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

}
