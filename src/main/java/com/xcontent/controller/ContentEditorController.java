package com.xcontent.controller;

import com.xcontent.model.Content;
import com.xcontent.model.Template;
import com.xcontent.service.ContentService;
import com.xcontent.service.TemplateService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class ContentEditorController {
    private static final Logger logger = LoggerFactory.getLogger(ContentEditorController.class);

    @FXML private TextArea contentTextArea;
    @FXML private ComboBox<Template> templateComboBox;
    @FXML private TextField hashtagField;
    @FXML private FlowPane hashtagContainer;
    @FXML private ListView<String> mediaListView;
    @FXML private Label characterCountLabel;

    private final ContentService contentService;
    private final TemplateService templateService;
    private Content currentContent;

    public ContentEditorController() {
        this.contentService = new ContentService();
        this.templateService = new TemplateService();
        this.currentContent = new Content();
    }

    @FXML
    public void initialize() {
        setupTextAreaListener();
        loadTemplates();
        setupTemplateComboBox();
    }

    private void setupTextAreaListener() {
        contentTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            int length = newValue.length();
            characterCountLabel.setText(length + "/280");
            
            if (length > 280) {
                contentTextArea.setStyle("-fx-text-fill: red;");
            } else {
                contentTextArea.setStyle("");
            }
        });
    }

    private void loadTemplates() {
        try {
            List<Template> templates = templateService.getAllTemplates();
            templateComboBox.getItems().addAll(templates);
        } catch (Exception e) {
            logger.error("Error loading templates", e);
            showError("Error", "Could not load templates");
        }
    }

    private void setupTemplateComboBox() {
        templateComboBox.setOnAction(event -> {
            Template selected = templateComboBox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                contentTextArea.setText(selected.getContent());
            }
        });
    }

    @FXML
    private void handleAddHashtag() {
        String hashtag = hashtagField.getText().trim();
        if (!hashtag.isEmpty()) {
            if (!hashtag.startsWith("#")) {
                hashtag = "#" + hashtag;
            }
            currentContent.getHashtags().add(hashtag);
            updateHashtagDisplay();
            hashtagField.clear();
        }
    }

    @FXML
    private void handleAddMedia() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
            new FileChooser.ExtensionFilter("Video Files", "*.mp4")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            currentContent.getMediaUrls().add(selectedFile.getAbsolutePath());
            updateMediaListView();
        }
    }

    @FXML
    private void handlePreview() {
        // TODO: プレビュー画面の実装
    }

    @FXML
    private void handleSaveDraft() {
        updateContentFromForm();
        try {
            contentService.saveDraft(currentContent);
            showInfo("Success", "Content saved as draft");
        } catch (Exception e) {
            logger.error("Error saving draft", e);
            showError("Error", "Could not save draft");
        }
    }

    @FXML
    private void handleSchedule() {
        // TODO: スケジュール画面の実装
    }

    @FXML
    private void handlePostNow() {
        updateContentFromForm();
        if (!currentContent.isValid()) {
            showError("Validation Error", "Content is not valid");
            return;
        }

        try {
            contentService.postContent(currentContent);
            showInfo("Success", "Content posted successfully");
            clearForm();
        } catch (Exception e) {
            logger.error("Error posting content", e);
            showError("Error", "Could not post content");
        }
    }

    private void updateContentFromForm() {
        currentContent.setText(contentTextArea.getText());
    }

    private void updateHashtagDisplay() {
        hashtagContainer.getChildren().clear();
        for (String hashtag : currentContent.getHashtags()) {
            Label label = new Label(hashtag);
            label.getStyleClass().add("hashtag-label");
            hashtagContainer.getChildren().add(label);
        }
    }

    private void updateMediaListView() {
        mediaListView.getItems().clear();
        mediaListView.getItems().addAll(currentContent.getMediaUrls());
    }

    private void clearForm() {
        contentTextArea.clear();
        hashtagField.clear();
        hashtagContainer.getChildren().clear();
        mediaListView.getItems().clear();
        currentContent = new Content();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
