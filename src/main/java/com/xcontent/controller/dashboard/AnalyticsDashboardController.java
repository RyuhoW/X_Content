package com.xcontent.controller.dashboard;

import com.xcontent.model.analytics.AnalyticsData;
import com.xcontent.service.analytics.RealTimeDataService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.function.Consumer;

public class AnalyticsDashboardController {
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsDashboardController.class);
    
    @FXML private Label activeUsersLabel;
    @FXML private Label engagementRateLabel;
    @FXML private Label responseTimeLabel;
    @FXML private ComboBox<String> timeRangeCombo;
    
    private final RealTimeDataService dataService;
    private final String subscriberId;

    public AnalyticsDashboardController() {
        this.dataService = new RealTimeDataService();
        this.subscriberId = UUID.randomUUID().toString();
    }

    @FXML
    public void initialize() {
        setupTimeRangeCombo();
        subscribeToUpdates();
        dataService.startRealTimeUpdates();
    }

    private void setupTimeRangeCombo() {
        timeRangeCombo.getItems().addAll(
            "Last 5 minutes",
            "Last 15 minutes",
            "Last hour",
            "Last 24 hours"
        );
        timeRangeCombo.setValue("Last 5 minutes");
    }

    private void subscribeToUpdates() {
        dataService.subscribe(subscriberId, (Consumer<AnalyticsData>) this::updateDashboard);
    }

    private void updateDashboard(AnalyticsData data) {
        try {
            activeUsersLabel.setText(String.valueOf(data.getActiveUsers()));
            engagementRateLabel.setText(
                String.format("%.2f%%", data.getEngagementRate() * 100));
            responseTimeLabel.setText(
                String.format("%.2fms", data.getAverageResponseTime()));
        } catch (Exception e) {
            logger.error("Error updating dashboard", e);
        }
    }

    @FXML
    private void handleRefresh() {
        // Manual refresh logic
    }

    public void cleanup() {
        dataService.unsubscribe(subscriberId);
        dataService.shutdown();
    }
}
