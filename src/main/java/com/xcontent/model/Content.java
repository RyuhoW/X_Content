package com.xcontent.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Content {
    private Long contentId;
    private Long accountId;
    private String text;
    private List<String> hashtags;
    private List<String> mediaUrls;
    private Long templateId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public Content() {
        this.hashtags = new ArrayList<>();
        this.mediaUrls = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Content(String text) {
        this();
        this.text = text;
    }

    // Getters and Setters
    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Utility methods
    public void addHashtag(String hashtag) {
        if (hashtag != null && !hashtag.isEmpty()) {
            this.hashtags.add(hashtag);
        }
    }

    public void addMediaUrl(String mediaUrl) {
        if (mediaUrl != null && !mediaUrl.isEmpty()) {
            this.mediaUrls.add(mediaUrl);
        }
    }

    public String getFormattedContent() {
        StringBuilder content = new StringBuilder(text);
        
        // Add hashtags
        if (!hashtags.isEmpty()) {
            content.append("\n");
            for (String hashtag : hashtags) {
                content.append("#").append(hashtag).append(" ");
            }
        }
        
        return content.toString().trim();
    }
} 