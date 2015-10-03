package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

// Takes Tweet objects and converts them into Views
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
     public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context,android.R.layout.simple_list_item_1, tweets);
    }

    // Overide and use our custom template.

}
