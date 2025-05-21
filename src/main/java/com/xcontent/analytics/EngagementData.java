package com.xcontent.analytics;

import java.time.LocalDateTime;
import java.util.List;

public class EngagementData {
    private List<Engagement> engagements;
    private String contentId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static class Engagement {
        private LocalDateTime timestamp;
        private String type;
        private int count;
        private double score;

        // Getters and Setters
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }

        public double getScore() { return score; }
        public void setScore(double score) { this.score = score; }
    }

    // Getters and Setters
    public List<Engagement> getEngagements() { return engagements; }
    public void setEngagements(List<Engagement> engagements) { this.engagements = engagements; }

    public String getContentId() { return contentId; }
    public void setContentId(String contentId) { this.contentId = contentId; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
} 