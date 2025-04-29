package com.xcontent.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainWindowController {
    private static final Logger logger = LoggerFactory.getLogger(MainWindowController.class);

    @FXML
    private BorderPane rootPane;

    @FXML
    public void initialize() {
        loadDashboard();
    }

    @FXML
    private void handleSettings() {
        // TODO: 設定画面の実装
        showAlert("Settings", "Settings functionality will be implemented soon.");
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    @FXML
    private void handleLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent loginView = loader.load();
            rootPane.setCenter(loginView);
        } catch (IOException e) {
            logger.error("Error loading login view", e);
            showError("Error", "Could not load login view");
        }
    }

    @FXML
    private void handleLogout() {
        // TODO: ログアウト処理の実装
        loadDashboard();
    }

    @FXML
    private void handleAbout() {
        showAlert("About", "X.com Content Generator\nVersion 1.0.0");
    }

    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
            Parent dashboard = loader.load();
            rootPane.setCenter(dashboard);
        } catch (IOException e) {
            logger.error("Error loading dashboard", e);
            showError("Error", "Could not load dashboard");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
