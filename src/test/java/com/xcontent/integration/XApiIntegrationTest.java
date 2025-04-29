package com.xcontent.integration;

import com.xcontent.api.XApiClient;
import com.xcontent.api.model.Tweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
public class XApiIntegrationTest {

    private XApiClient apiClient;

    @BeforeEach
    void setUp() {
        apiClient = new XApiClient();
    }

    @Test
    void postTweet_ShouldSucceed() {
        // Arrange
        String text = "Test tweet " + System.currentTimeMillis();

        // Act
        CompletableFuture<Tweet> future = apiClient.postTweet(text, List.of());

        // Assert
        Tweet tweet = future.join();
        assertNotNull(tweet.getId());
        assertEquals(text, tweet.getText());
    }

    @Test
    void rateLimit_ShouldBeRespected() {
        // Arrange
        int requestCount = 10;
        long startTime = System.currentTimeMillis();

        // Act
        for (int i = 0; i < requestCount; i++) {
            apiClient.postTweet("Test " + i, List.of()).join();
        }

        // Assert
        long duration = System.currentTimeMillis() - startTime;
        assertTrue(duration >= TimeUnit.SECONDS.toMillis(5), 
            "Rate limiting should prevent rapid requests");
    }
}
