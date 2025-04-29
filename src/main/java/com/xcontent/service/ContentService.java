package com.xcontent.service;

import com.xcontent.model.Content;
import com.xcontent.api.XApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentService {
    private static final Logger logger = LoggerFactory.getLogger(ContentService.class);
    private final XApiClient apiClient;

    public ContentService() {
        this.apiClient = new XApiClient();
    }

    public void saveDraft(Content content) {
        try {
            // TODO: Implement draft saving to database
            logger.info("Saving content as draft: {}", content.getId());
        } catch (Exception e) {
            logger.error("Error saving draft", e);
            throw new RuntimeException("Failed to save draft", e);
        }
    }

    public void postContent(Content content) {
        try {
            apiClient.postTweet(content.getText(), content.getMediaUrls())
                    .thenAccept(tweet -> {
                        logger.info("Content posted successfully: {}", tweet.getId());
                        // TODO: Update content status in database
                    })
                    .exceptionally(e -> {
                        logger.error("Error posting content", e);
                        throw new RuntimeException("Failed to post content", e);
                    });
        } catch (Exception e) {
            logger.error("Error posting content", e);
            throw new RuntimeException("Failed to post content", e);
        }
    }

    public void deleteContent(Long contentId) {
        try {
            // TODO: Implement content deletion
            logger.info("Deleting content: {}", contentId);
        } catch (Exception e) {
            logger.error("Error deleting content", e);
            throw new RuntimeException("Failed to delete content", e);
        }
    }
}
