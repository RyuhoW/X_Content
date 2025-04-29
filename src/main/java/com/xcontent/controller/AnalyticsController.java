package com.xcontent.controller;

import com.xcontent.model.analytics.PostAnalytics;
import com.xcontent.service.AnalyticsService;
import com.xcontent.service.ReportService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AnalyticsController implements Initializable {
    private static final Logger logger = LogManager.getLogger(AnalyticsController.class);

    @FXML private ComboBox<String> dateRangeSelector;
    @FXML private ComboBox<String> accountSelector;
    @FXML private Label totalImpressions;
    @FXML private Label totalEngagements;
    @FXML private Label engagementRate;
    @FXML private Label profileClicks;
    @FXML private LineChart<String, Number> engagementChart;
    @FXML private PieChart engagementBreakdown;
    @FXML private TableView<PostAnalytics> topPostsTable;
    @FXML private TableColumn<PostAnalytics, LocalDateTime> dateColumn;
    @FXML private TableColumn<PostAnalytics, String> contentColumn;
    @FXML private TableColumn<PostAnalytics, Integer> impressionsColumn;
    @FXML private TableColumn<PostAnalytics, Integer> engagementColumn;
    @FXML private TableColumn<PostAnalytics, Double> rateColumn;

    private final AnalyticsService analyticsService;
    private final ReportService reportService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AnalyticsController() {
        this.analyticsService = new AnalyticsService();
        this.reportService = new ReportService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupSelectors();
        setupTable();
        setupCharts();
        loadInitialData();
    }

    private void setupSelectors() {
        dateRangeSelector.getItems().addAll(
            "Last 7 Days",
            "Last 30 Days",
            "Last 90 Days",
            "Custom Range"
        );
        dateRangeSelector.getSelectionModel().selectFirst();

        accountSelector.getItems().addAll("@testuser", "@company_official");
        accountSelector.getSelectionModel().selectFirst();

        // Add listeners
        dateRangeSelector.setOnAction(e -> refreshData());
        accountSelector.setOnAction(e -> refreshData());
    }

    private void setupTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        dateColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(dateFormatter.format(date));
                }
            }
        });

        impressionsColumn.setCellValueFactory(new PropertyValueFactory<>("impressions"));
        engagementColumn.setCellValueFactory(new PropertyValueFactory<>("likes")); // Simplified
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("engagementRate"));
    }

    private void setupCharts() {
        engagementChart.setTitle("Engagement Trends");
        engagementBreakdown.setTitle("Engagement Types");
    }

    private void loadInitialData() {
        refreshData();
    }

    private void refreshData() {
        String dateRange = dateRangeSelector.getValue();
        String account = accountSelector.getValue();
        
        logger.info("Refreshing data for {} - {}", account, dateRange);
        
        // TODO: Implement actual data loading
        updateOverviewStats(0, 0, 0, 0);
        updateCharts();
    }

    private void updateOverviewStats(int impressions, int engagements, double rate, int clicks) {
        totalImpressions.setText(String.format("%,d", impressions));
        totalEngagements.setText(String.format("%,d", engagements));
        engagementRate.setText(String.format("%.1f%%", rate));
        profileClicks.setText(String.format("%,d", clicks));
    }

    private void updateCharts() {
        // TODO: Implement chart updates
        logger.info("Updating charts");
    }

    @FXML
    private void handleExportReport() {
        logger.info("Exporting analytics report");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Report");
        alert.setHeaderText(null);
        alert.setContentText("Report export started. You will be notified when it's ready.");
        alert.show();

        // TODO: Implement actual report generation
        reportService.generateReport(dateRangeSelector.getValue(), accountSelector.getValue())
            .thenAccept(success -> {
                if (success) {
                    logger.info("Report generated successfully");
                } else {
                    logger.error("Failed to generate report");
                }
            });
    }
} 