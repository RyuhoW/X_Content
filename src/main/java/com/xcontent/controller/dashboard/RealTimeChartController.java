package com.xcontent.controller.dashboard;

import com.xcontent.service.analytics.RealTimeDataService;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneOffset;
import java.util.UUID;

public class RealTimeChartController {
    private static final Logger logger = LoggerFactory.getLogger(RealTimeChartController.class);
    
    @FXML private LineChart<Number, Number> realTimeChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    
    private final RealTimeDataService dataService;
    private final String subscriberId;
    private final XYChart.Series<Number, Number> series;

    public RealTimeChartController() {
        this.dataService = new RealTimeDataService();
        this.subscriberId = UUID.randomUUID().toString();
        this.series = new XYChart.Series<>();
    }

    @FXML
    public void initialize() {
        setupChart();
        subscribeToUpdates();
    }

    private void setupChart() {
        realTimeChart.setTitle("Real-Time Engagement");
        series.setName("Engagement Rate");
        realTimeChart.getData().add(series);
        
        // Performance optimizations
        realTimeChart.setAnimated(false);
        realTimeChart.setCreateSymbols(false);
    }

    private void subscribeToUpdates() {
        dataService.subscribe(subscriberId, data -> {
            if (series.getData().size() > 50) {
                series.getData().remove(0);
            }
            
            series.getData().add(new XYChart.Data<>(
                data.getTimestamp().toEpochSecond(ZoneOffset.UTC),
                data.getEngagementRate()
            ));
        });
    }

    public void cleanup() {
        dataService.unsubscribe(subscriberId);
    }
}
