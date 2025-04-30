package com.xcontent.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.control.ComboBox;
import com.xcontent.service.ReportViewerService;
import org.springframework.stereotype.Component;

@Component
public class ReportViewerController {

    @FXML private WebView reportWebView;
    @FXML private ComboBox<String> reportSelector;
    private final ReportViewerService viewerService;

    public ReportViewerController(ReportViewerService viewerService) {
        this.viewerService = viewerService;
    }

    @FXML
    public void initialize() {
        setupReportSelector();
    }

    private void setupReportSelector() {
        reportSelector.setItems(viewerService.getAvailableReports());
        reportSelector.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> loadReport(newVal)
        );
    }

    @FXML
    private void handleExport() {
        String currentReport = reportSelector.getValue();
        if (currentReport != null) {
            viewerService.exportReport(currentReport);
        }
    }

    @FXML
    private void handlePrint() {
        reportWebView.getEngine().print();
    }

    @FXML
    private void handleRefresh() {
        String currentReport = reportSelector.getValue();
        if (currentReport != null) {
            loadReport(currentReport);
        }
    }

    private void loadReport(String reportName) {
        if (reportName != null) {
            String reportContent = viewerService.getReportContent(reportName);
            reportWebView.getEngine().loadContent(reportContent);
        }
    }
} 