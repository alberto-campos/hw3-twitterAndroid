package com.codepath.apps.restclienttemplate;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.HttpAuthHandler;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "mx3ToaqSqdRBcJxwSwoEWILmM";
	public static final String REST_CONSUMER_SECRET = "oigmbQVf7WMLDvnQjy7nCutfMeJN2p5nYbCPMdqGntRaKt3WgP";
	public static final String REST_CALLBACK_URL = "oauth://cptwittercodepath";

    public static final int COUNT = 25;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");

		// Specify the params
		RequestParams params = new RequestParams();
		params.put("count", COUNT);
		params.put("since_id", 1);

		// Execute the request
		getClient().get(apiUrl, params, handler);
	}

    public void getHomeTimeline(AsyncHttpResponseHandler handler, int maxId) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");

        // Specify the params
        RequestParams params = new RequestParams();
        params.put("count", COUNT);
        params.put("since_id", COUNT+1);
        params.put("max_id", maxId);

        // Execute the request
        getClient().get(apiUrl, params, handler);
    }


	public void getUserTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		// Specify the params
		RequestParams params = new RequestParams();
		params.put("count", COUNT);
		// Execute
		getClient().get(apiUrl, params, handler);
	}


	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		// Specify the params
		RequestParams params = new RequestParams();
		params.put("count", COUNT);
		params.put("screen_name", screenName);
		// Execute
		getClient().get(apiUrl, params, handler);
	}

	// get myProfile
	public void getMyProfile(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		// Execute
		getClient().get(apiUrl, handler);
	}


    // Compose tweet
    public void composeTweet(AsyncHttpResponseHandler handler, String msg) {
		String apiUrl = getApiUrl("statuses/update.json");

		// Specify the params
		RequestParams params = new RequestParams();
		params.put("status", msg);

		// Execute
		getClient().post(apiUrl, params, handler);

    }

	public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");

		// Specify the params
		RequestParams params = new RequestParams();
		params.put("count", COUNT);

		// Execute the request
		getClient().get(apiUrl, params, handler);

	}
}