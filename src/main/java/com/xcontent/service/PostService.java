package com.xcontent.service;

import com.xcontent.model.Content;
import com.xcontent.service.api.XApiClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class PostService {
    private static final Logger logger = LogManager.getLogger(PostService.class);
    private final XApiClient apiClient;

    public PostService(XApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public CompletableFuture<Boolean> postContent(Content content) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                XApiClient.TweetResponse response = apiClient.postTweet(content);
                logger.info("Content posted successfully, tweet ID: {}", response.getId());
                return true;
            } catch (IOException e) {
                logger.error("Failed to post content", e);
                return false;
            }
        });
    }

    public CompletableFuture<XApiClient.TweetResponse> getTweetDetails(String tweetId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return apiClient.getTweet(tweetId);
            } catch (IOException e) {
                logger.error("Failed to get tweet details", e);
                throw new RuntimeException(e);
            }
        });
    }
} 