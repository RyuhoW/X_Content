package com.xcontent.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private static final Logger logger = LogManager.getLogger(MainWindowController.class);

    @FXML private ComboBox<String> accountSelector;
    @FXML private Label userNameLabel;
    @FXML private Label planLabel;
    @FXML private Label statusLabel;
    @FXML private Label connectionStatus;
    @FXML private TabPane mainTabPane;
    @FXML private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing MainWindow controller");
        initializeUI();
    }

    private void initializeUI() {
        // Initialize account selector
        accountSelector.getItems().addAll("@testuser", "@company_official");
        accountSelector.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> handleAccountChange(newVal)
        );

        // Set initial status
        updateStatus("Application ready");
    }

    @FXML
    private void handleDashboard() {
        openTab("Dashboard", "dashboard");
    }

    @FXML
    private void handleNewContent() {
        openTab("New Content", "content-editor");
    }

    @FXML
    private void handleSchedule() {
        openTab("Schedule", "schedule");
    }

    @FXML
    private void handleAnalytics() {
        openTab("Analytics", "analytics");
    }

    @FXML
    private void handleTemplates() {
        openTab("Templates", "templates");
    }

    @FXML
    private void handleSettings() {
        openTab("Settings", "settings");
    }

    @FXML
    private void handleSwitchAccount() {
        logger.info("Switch account requested");
        // TODO: Implement account switching
    }

    @FXML
    private void handleManageAccounts() {
        logger.info("Manage accounts requested");
        // TODO: Implement account management
    }

    @FXML
    private void handleDocumentation() {
        logger.info("Documentation requested");
        // TODO: Show documentation
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("X.com Content Generator");
        alert.setContentText("Version 1.0.0\n© 2025 XContent");
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) contentArea.getScene().getWindow();
        stage.close();
    }

    private void openTab(String title, String fxmlName) {
        try {
            // Check if tab already exists
            for (Tab tab : mainTabPane.getTabs()) {
                if (tab.getText().equals(title)) {
                    mainTabPane.getSelectionModel().select(tab);
                    return;
                }
            }

            // Load new tab content
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlName + ".fxml"));
            Tab tab = new Tab(title, loader.load());
            mainTabPane.getTabs().add(tab);
            mainTabPane.getSelectionModel().select(tab);
            
            updateStatus("Opened " + title);
        } catch (IOException e) {
            logger.error("Failed to load " + fxmlName + ".fxml", e);
            showError("Failed to load " + title);
        }
    }

    private void handleAccountChange(String account) {
        logger.info("Switching to account: " + account);
        updateStatus("Switched to account: " + account);
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
        logger.info(message);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 