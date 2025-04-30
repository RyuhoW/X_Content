package com.xcontent.controller.dashboard;

import com.xcontent.service.dashboard.DashboardService;
import com.xcontent.service.dashboard.DataUpdateService;
import com.xcontent.model.dashboard.DashboardData;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class MainDashboardController {
    private static final Logger logger = LoggerFactory.getLogger(MainDashboardController.class);
    
    @FXML private Label pageTitle;
    @FXML private LineChart<String, Number> engagementChart;
    @FXML private ListView<String> activityList;
    
    private final DashboardService dashboardService;
    private final DataUpdateService dataUpdateService;
    private Timer updateTimer;

    public MainDashboardController() {
        this.dashboardService = new DashboardService();
        this.dataUpdateService = new DataUpdateService();
    }

    @FXML
    public void initialize() {
        pageTitle.setText("Dashboard");
        setupDataUpdateTimer();
        loadInitialData();
    }

    private void setupDataUpdateTimer() {
        updateTimer = new Timer(true);
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateDashboardData();
            }
        }, 0, 60000); // 1分ごとに更新
    }

    private void loadInitialData() {
        try {
            dashboardService.getInitialData()
                .thenAccept(this::updateUI)
                .exceptionally(e -> {
                    logger.error("Error loading initial data", e);
                    return null;
                });
        } catch (Exception e) {
            logger.error("Error in loadInitialData", e);
        }
    }

    private void updateDashboardData() {
        try {
            dataUpdateService.getLatestData()
                .thenAccept(this::updateUI)
                .exceptionally(e -> {
                    logger.error("Error updating dashboard data", e);
                    return null;
                });
        } catch (Exception e) {
            logger.error("Error in updateDashboardData", e);
        }
    }

    @FXML
    private void handleRefresh() {
        updateDashboardData();
    }

    private void updateUI(DashboardData data) {
        Platform.runLater(() -> {
            try {
                // TODO: 実際のUI更新ロジックを実装
                // 例: activityList.getItems().setAll(data.getRecentActivity());
            } catch (Exception e) {
                logger.error("Error updating UI", e);
            }
        });
    }

    public void cleanup() {
        if (updateTimer != null) {
            updateTimer.cancel();
            updateTimer = null;
        }
    }
}
