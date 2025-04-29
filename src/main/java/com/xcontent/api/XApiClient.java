package com.xcontent.api;

import com.xcontent.api.exception.XApiException;
import com.xcontent.api.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class XApiClient implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(XApiClient.class);
    
    private final HttpClient httpClient;
    private final XApiConfig config;
    private final RateLimiter rateLimiter;

    public XApiClient() {
        this.config = new XApiConfig();
        this.rateLimiter = new RateLimiter();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public CompletableFuture<Tweet> postTweet(String text, List<String> mediaIds) {
        if (!rateLimiter.tryAcquire("tweets")) {
            return CompletableFuture.failedFuture(
                new XApiException("Rate limit exceeded", 429, "TOO_MANY_REQUESTS"));
        }

        String url = config.getBaseUrl() + "/tweets";
        String json = createTweetJson(text, mediaIds);

        HttpRequest request = createAuthenticatedRequest(url, "POST", json);

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(this::handleResponse)
                .thenApply(this::parseTweetResponse)
                .exceptionally(e -> {
                    logger.error("Error posting tweet", e);
                    throw new XApiException("Failed to post tweet", e);
                });
    }

    public CompletableFuture<String> uploadMedia(byte[] media, String mediaType) {
        if (!rateLimiter.tryAcquire("media")) {
            return CompletableFuture.failedFuture(
                new XApiException("Media upload rate limit exceeded", 429, "TOO_MANY_REQUESTS"));
        }

        // メディアアップロードの実装
        // 実際のX APIではチャンク分割とステータス確認が必要
        return CompletableFuture.failedFuture(
            new UnsupportedOperationException("Media upload not implemented yet"));
    }

    private HttpRequest createAuthenticatedRequest(String url, String method, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + config.getApiKey())
                .header("Content-Type", "application/json")
                .method(method, HttpRequest.BodyPublishers.ofString(body))
                .build();
    }

    private String handleResponse(HttpResponse<String> response) {
        int statusCode = response.statusCode();
        if (statusCode >= 200 && statusCode < 300) {
            return response.body();
        }

        String errorMessage = String.format("API request failed with status %d: %s",
                statusCode, response.body());
        logger.error(errorMessage);

        throw new XApiException(errorMessage, statusCode,
                extractErrorCode(response.body()));
    }

    private Tweet parseTweetResponse(String json) {
        // JSON解析の実装
        // 実際の実装ではJacksonなどのライブラリを使用
        return new Tweet();
    }

    private String extractErrorCode(String responseBody) {
        // エラーコードの抽出ロジック
        // 実際の実装ではJSONパースが必要
        return "UNKNOWN_ERROR";
    }

    @Override
    public void close() {
        rateLimiter.shutdown();
    }
}
