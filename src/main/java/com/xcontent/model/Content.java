package com.xcontent.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
    private Long id;
    private final StringProperty text = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private LocalDateTime scheduledTime;
    private String status;
    private List<String> mediaUrls;
    private String[] tags;
    private String targetAudience;
    private String language;
    private Long userId;
    private Long accountId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> hashtags;
    private String type;

    public Content() {
        this.mediaUrls = new ArrayList<>();
        this.hashtags = new ArrayList<>();
        this.status = "draft";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Content(String text, String title, String type, String[] tags, 
                  String targetAudience, String language) {
        this();
        this.text.set(text);
        this.title.set(title);
        this.type = type;
        this.tags = tags;
        this.targetAudience = targetAudience;
        this.language = language;
        this.status = "draft";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text.get();
    }

    public void setText(String value) {
        text.set(value);
    }

    public StringProperty textProperty() {
        return text;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String value) {
        title.set(value);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @JsonProperty("target_audience")
    public String getTargetAudience() {
        return targetAudience;
    }

    @JsonProperty("target_audience")
    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    // Validation
    public boolean isValid() {
        String currentText = text.get();
        return currentText != null && !currentText.isEmpty() && currentText.length() <= 280;
    }
}
