package com.xcontent.analytics;

import java.time.LocalDateTime;

public class EngagementMetrics {
    private Long contentId;
    private int impressions;
    private int likes;
    private int retweets;
    private int replies;
    private double engagementRate;
    private LocalDateTime timestamp;

    public EngagementMetrics() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public int getImpressions() { return impressions; }
    public void setImpressions(int impressions) { 
        this.impressions = impressions;
        calculateEngagementRate();
    }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { 
        this.likes = likes;
        calculateEngagementRate();
    }

    public int getRetweets() { return retweets; }
    public void setRetweets(int retweets) { 
        this.retweets = retweets;
        calculateEngagementRate();
    }

    public int getReplies() { return replies; }
    public void setReplies(int replies) { 
        this.replies = replies;
        calculateEngagementRate();
    }

    public double getEngagementRate() { return engagementRate; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    private void calculateEngagementRate() {
        if (impressions > 0) {
            this.engagementRate = (double) (likes + retweets + replies) / impressions * 100;
        } else {
            this.engagementRate = 0.0;
        }
    }
}
