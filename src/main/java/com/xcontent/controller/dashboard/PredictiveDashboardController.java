package com.xcontent.controller.dashboard;

import com.xcontent.model.Content;
import com.xcontent.service.prediction.PredictionService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PredictiveDashboardController {
    private static final Logger logger = LoggerFactory.getLogger(PredictiveDashboardController.class);
    
    @FXML private ComboBox<String> contentTypeCombo;
    @FXML private ComboBox<String> timeRangeCombo;
    @FXML private Slider confidenceSlider;
    @FXML private ComboBox<String> modelVersionCombo;
    
    @FXML private TableView<PredictionService.PredictionResult> predictionHistoryTable;
    @FXML private TableColumn<PredictionService.PredictionResult, String> timestampColumn;
    @FXML private TableColumn<PredictionService.PredictionResult, String> contentColumn;
    @FXML private TableColumn<PredictionService.PredictionResult, Double> predictedColumn;
    @FXML private TableColumn<PredictionService.PredictionResult, Double> actualColumn;
    @FXML private TableColumn<PredictionService.PredictionResult, Double> accuracyColumn;

    private final PredictionService predictionService;
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public PredictiveDashboardController() {
        this.predictionService = new PredictionService();
    }

    @FXML
    public void initialize() {
        setupComboBoxes();
        setupTable();
        loadPredictionHistory();
    }

    private void setupComboBoxes() {
        contentTypeCombo.getItems().addAll(
            "Text Only",
            "Text with Media",
            "Text with Links",
            "Mixed Content"
        );
        
        timeRangeCombo.getItems().addAll(
            "Last Hour",
            "Last 24 Hours",
            "Last Week",
            "Last Month"
        );
        
        modelVersionCombo.getItems().addAll(
            "Latest",
            "v20240101",
            "v20231201"
        );
        
        contentTypeCombo.setValue("Text Only");
        timeRangeCombo.setValue("Last 24 Hours");
        modelVersionCombo.setValue("Latest");
    }

    private void setupTable() {
        timestampColumn.setCellValueFactory(
            data -> javafx.beans.binding.Bindings.createStringBinding(
                () -> data.getValue().getPredictionTime().format(DATE_FORMATTER)
            )
        );
        
        contentColumn.setCellValueFactory(
            data -> javafx.beans.binding.Bindings.createStringBinding(
                () -> data.getValue().getContent().getText()
            )
        );
        
        predictedColumn.setCellValueFactory(
            new PropertyValueFactory<>("predictedScore")
        );
        
        actualColumn.setCellValueFactory(
            new PropertyValueFactory<>("actualScore")
        );
        
        accuracyColumn.setCellValueFactory(
            data -> javafx.beans.binding.Bindings.createObjectBinding(
                () -> calculateAccuracy(data.getValue())
            )
        );
    }

    private void loadPredictionHistory() {
        predictionHistoryTable.getItems().clear();
        predictionHistoryTable.getItems().addAll(
            predictionService.getPredictionHistory()
        );
    }

    @FXML
    private void handleNewPrediction() {
        try {
            Content content = new Content(); // TODO: Show content input dialog
            
            predictionService.predictEngagement(content)
                .thenAccept(result -> {
                    predictionHistoryTable.getItems().add(0, result);
                })
                .exceptionally(e -> {
                    logger.error("Prediction failed", e);
                    showError("Prediction Failed", e.getMessage());
                    return null;
                });
        } catch (Exception e) {
            logger.error("Error creating new prediction", e);
            showError("Error", "Failed to create new prediction");
        }
    }

    private Double calculateAccuracy(PredictionService.PredictionResult result) {
        if (result.getActualScore() == null) {
            return null;
        }
        
        double predicted = result.getPredictedScore();
        double actual = result.getActualScore();
        return 1.0 - Math.abs(predicted - actual) / actual;
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
