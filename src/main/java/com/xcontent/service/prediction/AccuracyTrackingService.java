package com.xcontent.service.prediction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class AccuracyTrackingService {
    private static final Logger logger = LoggerFactory.getLogger(AccuracyTrackingService.class);
    
    private final ConcurrentLinkedQueue<PredictionService.PredictionResult> predictions;
    private static final int HISTORY_SIZE = 1000;
    private static final double ACCURACY_THRESHOLD = 0.1; // 10%誤差を許容

    public AccuracyTrackingService() {
        this.predictions = new ConcurrentLinkedQueue<>();
    }

    public void trackPrediction(PredictionService.PredictionResult result) {
        predictions.add(result);
        while (predictions.size() > HISTORY_SIZE) {
            predictions.poll();
        }
    }

    public double getCurrentAccuracy() {
        List<PredictionService.PredictionResult> completedPredictions = 
            predictions.stream()
                .filter(p -> p.getActualScore() != null)
                .collect(Collectors.toList());

        if (completedPredictions.isEmpty()) {
            return 1.0;
        }

        long accuratePredictions = completedPredictions.stream()
            .filter(this::isPredictionAccurate)
            .count();

        return (double) accuratePredictions / completedPredictions.size();
    }

    public List<PredictionService.PredictionResult> getPredictionHistory() {
        return new ArrayList<>(predictions);
    }

    public List<AccuracyMetric> getAccuracyMetrics() {
        List<AccuracyMetric> metrics = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        // 時間帯ごとの精度を計算
        for (int i = 0; i < 24; i++) {
            int hour = i;
            List<PredictionService.PredictionResult> hourlyPredictions = 
                predictions.stream()
                    .filter(p -> p.getPredictionTime().getHour() == hour)
                    .filter(p -> p.getActualScore() != null)
                    .collect(Collectors.toList());

            if (!hourlyPredictions.isEmpty()) {
                double accuracy = calculateAccuracy(hourlyPredictions);
                metrics.add(new AccuracyMetric(hour, accuracy));
            }
        }

        return metrics;
    }

    private boolean isPredictionAccurate(PredictionService.PredictionResult prediction) {
        double predicted = prediction.getPredictedScore();
        double actual = prediction.getActualScore();
        return Math.abs(predicted - actual) <= ACCURACY_THRESHOLD;
    }

    private double calculateAccuracy(List<PredictionService.PredictionResult> predictions) {
        long accuratePredictions = predictions.stream()
            .filter(this::isPredictionAccurate)
            .count();
        return (double) accuratePredictions / predictions.size();
    }

    public static class AccuracyMetric {
        private final int hour;
        private final double accuracy;

        public AccuracyMetric(int hour, double accuracy) {
            this.hour = hour;
            this.accuracy = accuracy;
        }

        public int getHour() { return hour; }
        public double getAccuracy() { return accuracy; }
    }
}
