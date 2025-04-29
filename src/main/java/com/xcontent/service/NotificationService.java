package com.xcontent.service;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

public class NotificationService {
    private static final Logger logger = LogManager.getLogger(NotificationService.class);

    public void showNotification(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.show();
            
            logger.info("Notification shown: {}", title);
        });
    }

    public CompletableFuture<ButtonType> showConfirmation(String title, String message) {
        CompletableFuture<ButtonType> future = new CompletableFuture<>();
        
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            
            alert.showAndWait().ifPresent(future::complete);
            logger.info("Confirmation dialog shown: {}", title);
        });
        
        return future;
    }

    public void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.show();
            
            logger.error("Error notification shown: {}", title);
        });
    }
} 