package com.xcontent.model;

import java.time.LocalDateTime;
import java.nio.file.Path;

public class ReportGenerationHistory {
    private Long id;
    private ReportConfiguration configuration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String errorMessage;
    private Path outputPath;

    public ReportGenerationHistory(ReportConfiguration configuration) {
        this.configuration = configuration;
        this.startTime = LocalDateTime.now();
        this.status = "PROCESSING";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ReportConfiguration configuration) {
        this.configuration = configuration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Path getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(Path outputPath) {
        this.outputPath = outputPath;
    }
} 