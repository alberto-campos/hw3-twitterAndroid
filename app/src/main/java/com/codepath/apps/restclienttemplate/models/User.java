package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    // List attributes
    // Deserialize the user json ==> User

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String profileBannerUrl;
    private String tagline;
    private int followersCount;
    private int followingsCount;
    private int statusesCount;

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

    public String getProfileBannerUrl () {
        return profileBannerUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return followingsCount;
    }

    public int getStatusesCount() {return statusesCount; }

    public static User fromJSON(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.profileBannerUrl = json.getString("profile_banner_url");
            u.tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingsCount = json.getInt("friends_count");
            u.statusesCount = json.getInt("statuses_count");
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return u;
    }

}
