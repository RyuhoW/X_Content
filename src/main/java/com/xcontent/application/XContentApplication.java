package com.xcontent.application;

import com.xcontent.config.DatabaseConfig;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XContentApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(XContentApplication.class);

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
            primaryStage.setTitle("X.com Content Generator");
            primaryStage.show();
            
            logger.info("Application started successfully");
        } catch (Exception e) {
            logger.error("Failed to start application", e);
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
