package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile {
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static Profile fromJSON(JSONObject json) {
        Profile p = new Profile(json);
        try {
            p.name = json.getString("name");
            p.uid = json.getLong("id");
            p.screenName = json.getString("screen_name");
            p.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return p;
    }

    public  Profile (JSONObject json) {
        try {
            name = json.getString("name");
            uid = json.getLong("id");
            screenName = json.getString("screen_name");
            profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
