package com.xcontent.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainApp extends Application {
    private static final Logger logger = LogManager.getLogger(MainApp.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
            
            primaryStage.setTitle("X.com Content Generator");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            logger.info("Application started successfully");
        } catch (Exception e) {
            logger.error("Failed to start application", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        logger.info("Initializing application");
    }

    @Override
    public void stop() {
        logger.info("Application shutting down");
    }

    public static void main(String[] args) {
        launch(args);
    }
} 