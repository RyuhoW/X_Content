package com.xcontent.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Content {
    private Long id;
    private String text;
    private List<String> mediaUrls;
    private List<String> hashtags;
    private String status;
    private Long userId;
    private Long accountId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Content() {
        this.mediaUrls = new ArrayList<>();
        this.hashtags = new ArrayList<>();
        this.status = "draft";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<String> getMediaUrls() { return mediaUrls; }
    public void setMediaUrls(List<String> mediaUrls) { this.mediaUrls = mediaUrls; }

    public List<String> getHashtags() { return hashtags; }
    public void setHashtags(List<String> hashtags) { this.hashtags = hashtags; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Validation
    public boolean isValid() {
        return text != null && !text.trim().isEmpty() && text.length() <= 280;
    }
}
