package com.xcontent.ml;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class PythonMLClient {
    private static final Logger logger = LoggerFactory.getLogger(PythonMLClient.class);
    private static final String API_BASE_URL = "http://localhost:8000";
    
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public PythonMLClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public CompletableFuture<PredictionResult> predictEngagement(Content content) {
        try {
            String json = objectMapper.writeValueAsString(content);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + "/predict"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (response.statusCode() != 200) {
                            throw new RuntimeException("Prediction API error: " + response.body());
                        }
                        try {
                            return objectMapper.readValue(response.body(), PredictionResult.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error parsing prediction result", e);
                        }
                    });
        } catch (Exception e) {
            logger.error("Error in engagement prediction", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public static class PredictionResult {
        private double engagementScore;
        private double confidence;

        // Getters and Setters
        public double getEngagementScore() { return engagementScore; }
        public void setEngagementScore(double engagementScore) { 
            this.engagementScore = engagementScore; 
        }
        
        public double getConfidence() { return confidence; }
        public void setConfidence(double confidence) { 
            this.confidence = confidence; 
        }
    }
} 