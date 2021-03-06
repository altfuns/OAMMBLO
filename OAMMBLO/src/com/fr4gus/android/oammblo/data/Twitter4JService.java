package com.fr4gus.android.oammblo.data;

import java.util.LinkedList;
import java.util.List;

import oauth.signpost.OAuth;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

import com.fr4gus.android.oammblo.bo.Tweet;
import com.fr4gus.android.oammblo.util.LogIt;

public class Twitter4JService implements TwitterService {
    public static final String STORE_KEY = "twitter-store";

    public static final String STORE_TOKEN = "token";

    public static final String STORE_SECRET_TOKEN = "secret-token";

    public static final String CONSUMER_KEY = "GLuVY7Um6ii04aDMt2AzwA";

    public static final String CONSUMER_SECRET_KEY = "y5vz978MnTyl1Xk3r1aXjqAkLFTVP0wawwUtTyEKEs";

    public static final String OAUTH_CALLBACK_SCHEME = "x-oauth-twitter";

    private static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://callback";

    // Current user keys
    private static AccessToken accessToken;

    private CommonsHttpOAuthConsumer consumer = null;

    private CommonsHttpOAuthProvider provider = null;

    Twitter twitter;

    @Override
    public List<Tweet> getTimeline() throws TwitterException {
    	List<Tweet> result = new LinkedList<Tweet>();
    	twitter = new TwitterFactory().getInstance();
    	User user = twitter.verifyCredentials();
    	List<Status> statusCollection = twitter.getHomeTimeline();
    	for(Status status: statusCollection){
    		result.add(new Tweet(status.getCreatedAt().getTime()
    				, status.getUser().getName(),status.getText()));
    	}
        return result;
    }

    @Override
    public boolean checkForSavedLogin(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(STORE_KEY, Context.MODE_PRIVATE);
        String token = prefs.getString(STORE_TOKEN, null);
        String secret = prefs.getString(STORE_SECRET_TOKEN, null);
        if (token != null && secret != null) {
            accessToken = new AccessToken(token, secret);
        }
        return accessToken != null;
    }

    @Override
    public void requestOAuthAccessToken(Context ctx) {
        try {
            consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET_KEY);

            provider = new CommonsHttpOAuthProvider("https://api.twitter.com/oauth/request_token",
                    "https://api.twitter.com/oauth/access_token", "https://api.twitter.com/oauth/authorize");
            String oauthUrl = provider.retrieveRequestToken(consumer, OAUTH_CALLBACK_URL);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(oauthUrl));
            ctx.startActivity(myIntent);
        } catch (Exception e) {
            LogIt.e(this, e, "ERRROR" + e.getMessage());
        }
    }

    public boolean authorize(Context context, Uri uriData) {
        String verifier = null;
        if (uriData != null && uriData.getScheme().equals(OAUTH_CALLBACK_SCHEME)) {
            LogIt.d(this, "callback: " + uriData.getPath());
            verifier = uriData.getQueryParameter(OAuth.OAUTH_VERIFIER);
            LogIt.d(this, "verifier: " + verifier);
        }
        try {
            if (accessToken == null) {
                provider.retrieveAccessToken(consumer, verifier);
                accessToken = new AccessToken(consumer.getToken(), consumer.getConsumerSecret());

                saveSession(context);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveSession(Context context) {
        Editor editor = context.getSharedPreferences(STORE_KEY, Context.MODE_PRIVATE).edit();
        editor.putString(STORE_TOKEN, accessToken.getToken());
        editor.putString(STORE_SECRET_TOKEN, accessToken.getTokenSecret());
        return editor.commit();
    }
}
