package com.xcontent.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class TemplatesController implements Initializable {
    private static final Logger logger = LogManager.getLogger(TemplatesController.class);

    @FXML private ComboBox<String> categorySelector;
    @FXML private TableView<?> templateTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupCategorySelector();
    }

    private void setupCategorySelector() {
        categorySelector.getItems().addAll(
            "All",
            "General",
            "Product",
            "Marketing",
            "Support"
        );
        categorySelector.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleNewTemplate() {
        logger.info("Creating new template");
        // TODO: Implement template creation
    }
} 