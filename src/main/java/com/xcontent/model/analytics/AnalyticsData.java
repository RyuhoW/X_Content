package com.xcontent.model.analytics;

import java.time.LocalDateTime;

public class AnalyticsData {
    private String id;
    private LocalDateTime timestamp;
    private int activeUsers;
    private double engagementRate;
    private double averageResponseTime;
    private int impressions;
    private int interactions;

    // デフォルトコンストラクタ
    public AnalyticsData() {
        this.timestamp = LocalDateTime.now();
    }

    // 主要フィールドを含むコンストラクタ
    public AnalyticsData(String id, int activeUsers, double engagementRate, double averageResponseTime) {
        this();
        this.id = id;
        this.activeUsers = activeUsers;
        this.engagementRate = engagementRate;
        this.averageResponseTime = averageResponseTime;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    public double getEngagementRate() {
        return engagementRate;
    }

    public void setEngagementRate(double engagementRate) {
        this.engagementRate = engagementRate;
    }

    public double getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(double averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public int getInteractions() {
        return interactions;
    }

    public void setInteractions(int interactions) {
        this.interactions = interactions;
    }

    // エンゲージメント率の計算メソッド
    public void calculateEngagementRate() {
        if (impressions > 0) {
            this.engagementRate = (double) interactions / impressions;
        } else {
            this.engagementRate = 0.0;
        }
    }
} 