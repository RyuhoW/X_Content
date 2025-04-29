package com.xcontent.analytics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PerformanceData {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<EngagementMetrics> metrics;
    private double averageEngagementRate;
    private int totalImpressions;
    private int totalEngagements;

    public PerformanceData() {
        this.metrics = new ArrayList<>();
    }

    public void addMetrics(EngagementMetrics metric) {
        metrics.add(metric);
        calculateAggregates();
    }

    private void calculateAggregates() {
        this.totalImpressions = metrics.stream()
                .mapToInt(EngagementMetrics::getImpressions)
                .sum();

        this.totalEngagements = metrics.stream()
                .mapToInt(m -> m.getLikes() + m.getRetweets() + m.getReplies())
                .sum();

        if (totalImpressions > 0) {
            this.averageEngagementRate = (double) totalEngagements / totalImpressions * 100;
        }
    }

    // Getters and Setters
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public List<EngagementMetrics> getMetrics() { return metrics; }
    public void setMetrics(List<EngagementMetrics> metrics) { 
        this.metrics = metrics;
        calculateAggregates();
    }

    public double getAverageEngagementRate() { return averageEngagementRate; }
    public int getTotalImpressions() { return totalImpressions; }
    public int getTotalEngagements() { return totalEngagements; }
}
