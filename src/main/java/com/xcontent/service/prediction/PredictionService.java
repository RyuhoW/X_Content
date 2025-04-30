package com.xcontent.service.prediction;

import com.xcontent.ml.PythonMLClient;
import com.xcontent.model.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PredictionService {
    private static final Logger logger = LoggerFactory.getLogger(PredictionService.class);
    
    private final PythonMLClient mlClient;
    private final AccuracyTrackingService accuracyTracker;
    private final Map<String, PredictionResult> predictionCache;

    public PredictionService() {
        this.mlClient = new PythonMLClient();
        this.accuracyTracker = new AccuracyTrackingService();
        this.predictionCache = new ConcurrentHashMap<>();
    }

    public CompletableFuture<PredictionResult> predictEngagement(Content content) {
        try {
            String cacheKey = generateCacheKey(content);
            PredictionResult cachedResult = predictionCache.get(cacheKey);
            
            if (cachedResult != null && !isCacheExpired(cachedResult)) {
                return CompletableFuture.completedFuture(cachedResult);
            }

            return mlClient.predictEngagement(content)
                .thenApply(result -> {
                    PredictionResult predictionResult = new PredictionResult(
                        content,
                        result.getEngagementScore(),
                        result.getConfidence(),
                        LocalDateTime.now()
                    );
                    
                    predictionCache.put(cacheKey, predictionResult);
                    accuracyTracker.trackPrediction(predictionResult);
                    
                    return predictionResult;
                })
                .exceptionally(e -> {
                    logger.error("Prediction failed", e);
                    throw new RuntimeException("Prediction failed", e);
                });
        } catch (Exception e) {
            logger.error("Error in prediction service", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public List<PredictionResult> getPredictionHistory() {
        return accuracyTracker.getPredictionHistory();
    }

    public double getCurrentAccuracy() {
        return accuracyTracker.getCurrentAccuracy();
    }

    private String generateCacheKey(Content content) {
        return content.getText().hashCode() + "_" + 
               content.getScheduledTime().toString();
    }

    private boolean isCacheExpired(PredictionResult result) {
        return LocalDateTime.now().minusHours(1)
            .isAfter(result.getPredictionTime());
    }

    public static class PredictionResult {
        private final Content content;
        private final double predictedScore;
        private final double confidence;
        private final LocalDateTime predictionTime;
        private Double actualScore;

        public PredictionResult(Content content, 
                              double predictedScore, 
                              double confidence,
                              LocalDateTime predictionTime) {
            this.content = content;
            this.predictedScore = predictedScore;
            this.confidence = confidence;
            this.predictionTime = predictionTime;
        }

        // Getters
        public Content getContent() { return content; }
        public double getPredictedScore() { return predictedScore; }
        public double getConfidence() { return confidence; }
        public LocalDateTime getPredictionTime() { return predictionTime; }
        public Double getActualScore() { return actualScore; }

        public void setActualScore(Double actualScore) {
            this.actualScore = actualScore;
        }
    }
}
