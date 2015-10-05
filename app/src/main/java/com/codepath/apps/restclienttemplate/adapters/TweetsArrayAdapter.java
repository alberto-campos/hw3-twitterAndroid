package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;
import java.util.Locale;


// Takes Tweet objects and converts them into Views
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {


    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

     public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context,android.R.layout.simple_list_item_1, tweets);
    }

    // Overide and use our custom template.


    // Apply ViewHOlder Patter to all adapters It's faster


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
        TextView tvRealName = (TextView) convertView.findViewById(R.id.tvRealName);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);


        PrettyTime p = new PrettyTime(new Locale("en"));

        tvScreenName.setText("@" + tweet.getUser().getScreenName());
        tvRealName.setText(tweet.getUser().getName());

        Date myDate = new java.util.Date(tweet.getCreatedAt());

        long t=myDate.getTime();
        Date afterRemoving10Mins=new Date(t - (55 * ONE_MINUTE_IN_MILLIS));

        tvTimestamp.setText(p.format(afterRemoving10Mins));

        tvBody.setText(Html.fromHtml(tweet.getBody()));
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        return  convertView;
    }
}
