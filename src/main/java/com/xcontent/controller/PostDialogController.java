package com.xcontent.controller;

import com.xcontent.model.Content;
import com.xcontent.service.PostService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PostDialogController {
    private static final Logger logger = LogManager.getLogger(PostDialogController.class);

    @FXML private TextArea contentPreview;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;

    private final PostService postService;
    private Content content;
    private Stage dialogStage;

    public PostDialogController(PostService postService) {
        this.postService = postService;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setContent(Content content) {
        this.content = content;
        contentPreview.setText(content.getText());
    }

    @FXML
    private void handlePost() {
        progressBar.setVisible(true);
        statusLabel.setText("Posting...");

        postService.postContent(content)
            .thenAccept(success -> {
                if (success) {
                    logger.info("Post successful");
                    closeDialog();
                } else {
                    logger.error("Post failed");
                    showError("Failed to post content");
                }
            })
            .exceptionally(throwable -> {
                logger.error("Error posting content", throwable);
                showError("An error occurred while posting");
                return null;
            });
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        dialogStage.close();
    }

    private void showError(String message) {
        statusLabel.setText(message);
        progressBar.setVisible(false);
    }
} 