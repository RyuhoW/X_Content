package com.xcontent.model.dashboard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DashboardData {
    private int totalPosts;
    private double engagementRate;
    private int impressions;
    private int scheduledPosts;
    private List<String> recentActivity;
    private LocalDateTime lastUpdated;

    public DashboardData() {
        this.recentActivity = new ArrayList<>();
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters and Setters
    public int getTotalPosts() { return totalPosts; }
    public void setTotalPosts(int totalPosts) { this.totalPosts = totalPosts; }

    public double getEngagementRate() { return engagementRate; }
    public void setEngagementRate(double engagementRate) { this.engagementRate = engagementRate; }

    public int getImpressions() { return impressions; }
    public void setImpressions(int impressions) { this.impressions = impressions; }

    public int getScheduledPosts() { return scheduledPosts; }
    public void setScheduledPosts(int scheduledPosts) { this.scheduledPosts = scheduledPosts; }

    public List<String> getRecentActivity() { return recentActivity; }
    public void setRecentActivity(List<String> recentActivity) { this.recentActivity = recentActivity; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
} 