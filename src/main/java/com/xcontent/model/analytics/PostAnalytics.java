package com.xcontent.model.analytics;

import java.time.LocalDateTime;

public class PostAnalytics {
    private Long analyticsId;
    private Long postId;
    private LocalDateTime timestamp;
    private int impressions;
    private int likes;
    private int replies;
    private int reposts;
    private int quotes;
    private int profileClicks;
    private int linkClicks;

    // Getters and Setters
    public Long getAnalyticsId() { return analyticsId; }
    public void setAnalyticsId(Long analyticsId) { this.analyticsId = analyticsId; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public int getImpressions() { return impressions; }
    public void setImpressions(int impressions) { this.impressions = impressions; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public int getReplies() { return replies; }
    public void setReplies(int replies) { this.replies = replies; }

    public int getReposts() { return reposts; }
    public void setReposts(int reposts) { this.reposts = reposts; }

    public int getQuotes() { return quotes; }
    public void setQuotes(int quotes) { this.quotes = quotes; }

    public int getProfileClicks() { return profileClicks; }
    public void setProfileClicks(int profileClicks) { this.profileClicks = profileClicks; }

    public int getLinkClicks() { return linkClicks; }
    public void setLinkClicks(int linkClicks) { this.linkClicks = linkClicks; }

    // Utility methods
    public double getEngagementRate() {
        if (impressions == 0) return 0;
        return (double) (likes + replies + reposts + quotes) / impressions * 100;
    }
} 