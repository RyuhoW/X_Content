package com.xcontent.config;

import com.xcontent.util.ConfigManager;

public class XApiConfig {
    private static final ConfigManager config = ConfigManager.getInstance();

    // API Endpoints
    public static final String API_BASE_URL = "https://api.twitter.com/2";
    public static final String OAUTH2_TOKEN_URL = "https://api.twitter.com/2/oauth2/token";
    public static final String OAUTH2_AUTHORIZE_URL = "https://twitter.com/i/oauth2/authorize";

    // OAuth 2.0 settings
    public static final String CLIENT_ID = config.getProperty("x.client.id");
    public static final String CLIENT_SECRET = config.getProperty("x.client.secret");
    public static final String REDIRECT_URI = "http://localhost:8080/callback";
    public static final String SCOPE = "tweet.read tweet.write users.read offline.access";

    // API Endpoints
    public static final String TWEETS_ENDPOINT = API_BASE_URL + "/tweets";
    public static final String USERS_ENDPOINT = API_BASE_URL + "/users";
} 