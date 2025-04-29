package com.xcontent.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tweet {
    private String id;
    private String text;
    private List<String> mediaIds;
    private LocalDateTime createdAt;
    private TweetMetrics metrics;

    public Tweet() {
        this.mediaIds = new ArrayList<>();
        this.metrics = new TweetMetrics();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<String> getMediaIds() { return mediaIds; }
    public void setMediaIds(List<String> mediaIds) { this.mediaIds = mediaIds; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public TweetMetrics getMetrics() { return metrics; }
    public void setMetrics(TweetMetrics metrics) { this.metrics = metrics; }

    public static class TweetMetrics {
        private int impressionCount;
        private int likeCount;
        private int retweetCount;
        private int replyCount;

        // Getters and Setters
        public int getImpressionCount() { return impressionCount; }
        public void setImpressionCount(int impressionCount) { this.impressionCount = impressionCount; }

        public int getLikeCount() { return likeCount; }
        public void setLikeCount(int likeCount) { this.likeCount = likeCount; }

        public int getRetweetCount() { return retweetCount; }
        public void setRetweetCount(int retweetCount) { this.retweetCount = retweetCount; }

        public int getReplyCount() { return replyCount; }
        public void setReplyCount(int replyCount) { this.replyCount = replyCount; }
    }
}
