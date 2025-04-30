package com.xcontent.analytics;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class RAnalyticsClient {
    private static final Logger logger = LoggerFactory.getLogger(RAnalyticsClient.class);
    private static final String API_BASE_URL = "http://localhost:8000";
    
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RAnalyticsClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public CompletableFuture<AnalyticsResult> analyzeEngagement(EngagementData data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + "/analyze"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (response.statusCode() != 200) {
                            throw new RuntimeException("Analytics API error: " + response.body());
                        }
                        try {
                            return objectMapper.readValue(response.body(), AnalyticsResult.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error parsing analytics result", e);
                        }
                    });
        } catch (Exception e) {
            logger.error("Error in engagement analysis", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public static class AnalyticsResult {
        private EngagementTrends trends;
        private TimeSeriesForecast forecast;
        private StatisticalTests statistics;

        // Getters and Setters
        public EngagementTrends getTrends() { return trends; }
        public void setTrends(EngagementTrends trends) { this.trends = trends; }
        
        public TimeSeriesForecast getForecast() { return forecast; }
        public void setForecast(TimeSeriesForecast forecast) { this.forecast = forecast; }
        
        public StatisticalTests getStatistics() { return statistics; }
        public void setStatistics(StatisticalTests statistics) { this.statistics = statistics; }
    }
} 