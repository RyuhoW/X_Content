package com.xcontent.service.api;

import com.google.gson.Gson;
import com.xcontent.config.XApiConfig;
import com.xcontent.model.Content;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class XApiClient {
    private static final Logger logger = LogManager.getLogger(XApiClient.class);
    private final OkHttpClient client;
    private final Gson gson;
    private String accessToken;

    public XApiClient() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public TweetResponse postTweet(Content content) throws IOException {
        TweetRequest tweetRequest = new TweetRequest(content.getText());
        
        RequestBody body = RequestBody.create(
            MediaType.parse("application/json"),
            gson.toJson(tweetRequest)
        );

        Request request = new Request.Builder()
            .url(XApiConfig.TWEETS_ENDPOINT)
            .post(body)
            .header("Authorization", "Bearer " + accessToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response " + response);
            }
            return gson.fromJson(response.body().string(), TweetResponse.class);
        }
    }

    public TweetResponse getTweet(String tweetId) throws IOException {
        Request request = new Request.Builder()
            .url(XApiConfig.TWEETS_ENDPOINT + "/" + tweetId)
            .get()
            .header("Authorization", "Bearer " + accessToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response " + response);
            }
            return gson.fromJson(response.body().string(), TweetResponse.class);
        }
    }

    // Request/Response classes
    private static class TweetRequest {
        private final String text;

        public TweetRequest(String text) {
            this.text = text;
        }
    }

    public static class TweetResponse {
        private String id;
        private String text;
        private String created_at;

        // Getters and setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(String createdAt) {
            this.created_at = createdAt;
        }
    }
} 