package com.xcontent.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ContentEditorController implements Initializable {
    private static final Logger logger = LogManager.getLogger(ContentEditorController.class);

    @FXML private ComboBox<String> templateSelector;
    @FXML private TextArea contentArea;
    @FXML private Label characterCount;
    @FXML private TextField hashtagInput;
    @FXML private FlowPane hashtagContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupContentArea();
        setupTemplateSelector();
    }

    private void setupContentArea() {
        contentArea.textProperty().addListener((obs, oldText, newText) -> {
            int count = newText.length();
            characterCount.setText(count + "/280");
            
            if (count > 280) {
                characterCount.setStyle("-fx-text-fill: red;");
            } else {
                characterCount.setStyle("-fx-text-fill: black;");
            }
        });
    }

    private void setupTemplateSelector() {
        templateSelector.getItems().addAll(
            "General Update",
            "Product Announcement",
            "Question",
            "Poll"
        );
    }

    @FXML
    private void handleSaveTemplate() {
        String content = contentArea.getText();
        if (content.isEmpty()) {
            showError("Content cannot be empty");
            return;
        }
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save Template");
        dialog.setHeaderText("Enter template name:");
        dialog.showAndWait().ifPresent(name -> {
            logger.info("Saving template: {}", name);
            // TODO: Implement template saving
        });
    }

    @FXML
    private void handleAiOptimize() {
        // TODO: Implement AI optimization
        logger.info("AI optimization requested");
    }

    @FXML
    private void handleSchedule() {
        // TODO: Implement scheduling
        logger.info("Scheduling requested");
    }

    @FXML
    private void handlePreview() {
        Alert preview = new Alert(Alert.AlertType.INFORMATION);
        preview.setTitle("Preview");
        preview.setHeaderText(null);
        preview.setContentText(contentArea.getText());
        preview.showAndWait();
    }

    @FXML
    private void handlePost() {
        if (contentArea.getText().isEmpty()) {
            showError("Content cannot be empty");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Post");
        confirm.setHeaderText("Are you sure you want to post this content?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                logger.info("Posting content");
                // TODO: Implement posting
            }
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 