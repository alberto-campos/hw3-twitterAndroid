package com.codepath.apps.restclienttemplate.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.adapters.TweetsArrayAdapter;
import com.codepath.apps.restclienttemplate.helper.EndlessScrollListener;
import com.codepath.apps.restclienttemplate.models.Profile;
import com.codepath.apps.restclienttemplate.models.Tweet;

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
}
