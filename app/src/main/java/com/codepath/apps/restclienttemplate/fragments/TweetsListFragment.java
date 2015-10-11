package com.codepath.apps.restclienttemplate.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.activities.ComposeActivity;
import com.codepath.apps.restclienttemplate.activities.ProfileActivity;
import com.codepath.apps.restclienttemplate.adapters.TweetsArrayAdapter;
import com.codepath.apps.restclienttemplate.helper.EndlessScrollListener;
import com.codepath.apps.restclienttemplate.models.Profile;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_tweet_list, parent, false);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);

        // Connect adapter to listView
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreTweets(page);
                return true;
            }
        });

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO: pass the user's id.
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", "dbykovskyy");
                startActivity(i);
            }
        });

      //  setupActionBar();

        return v;
    }



    @Override
    public void onCreate( Bundle savedInstanceState) {

        // Create the array List
        tweets = new ArrayList<>();
        // Construct the adapter from Dsource
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);


        super.onCreate(savedInstanceState);
    }

    public void customLoadMoreTweets(int page) {
       // populateTimeline();
    }

    public void addAll(List<Tweet> tweets) {

        aTweets.addAll(tweets);
    }


    private void setupActionBar() {

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.str_home);
        actionBar.setIcon(R.mipmap.ic_twitter);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        actionBar.show();
    }

}
