package com.xcontent.application;

import com.xcontent.config.DatabaseConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XContentApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(XContentApplication.class);
    private static final String TITLE = "X.com Content Generator";
    private static final double MIN_WIDTH = 800;
    private static final double MIN_HEIGHT = 600;

    @Override
    public void init() {
        try {
            DatabaseConfig.initialize();
            logger.info("Database initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
            BorderPane root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());

            primaryStage.setTitle(TITLE);
            primaryStage.setMinWidth(MIN_WIDTH);
            primaryStage.setMinHeight(MIN_HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();

            logger.info("Application UI started successfully");
        } catch (Exception e) {
            logger.error("Failed to start application UI", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        DatabaseConfig.shutdown();
        logger.info("Application shutting down");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
