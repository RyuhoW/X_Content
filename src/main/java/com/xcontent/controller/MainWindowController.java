package com.xcontent.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    private static final Logger logger = LogManager.getLogger(MainWindowController.class);

    @FXML
    private VBox mainContainer;

    @FXML
    private Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing MainWindow controller");
        statusLabel.setText("Ready");
    }

    @FXML
    private void handleNewContent() {
        logger.info("New content action triggered");
        statusLabel.setText("Creating new content...");
    }

    @FXML
    private void handleSchedule() {
        logger.info("Schedule action triggered");
        statusLabel.setText("Opening scheduler...");
    }

    @FXML
    private void handleAnalytics() {
        logger.info("Analytics action triggered");
        statusLabel.setText("Loading analytics...");
    }
} 