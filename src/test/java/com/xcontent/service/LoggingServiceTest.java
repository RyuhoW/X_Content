package com.xcontent.service;

import com.xcontent.model.AuditLog;
import com.xcontent.repository.AuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoggingServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    private LoggingService loggingService;

    @BeforeEach
    void setUp() {
        loggingService = new LoggingService(auditLogRepository);
    }

    @Test
    void logReportGeneration_ShouldSaveAuditLog() {
        // Arrange
        String reportId = "test-report-1";
        String status = "SUCCESS";
        String details = "Report generated successfully";

        // Act
        loggingService.logReportGeneration(reportId, status, details);

        // Assert
        verify(auditLogRepository).save(argThat(log -> 
            log.getEventType().equals("REPORT_GENERATION") &&
            log.getResourceId().equals(reportId) &&
            log.getStatus().equals(status) &&
            log.getDetails().equals(details)
        ));
    }

    @Test
    void logError_ShouldSaveErrorLog() {
        // Arrange
        String message = "Test error";
        Exception error = new RuntimeException("Test exception");

        // Act
        loggingService.logError(message, error);

        // Assert
        verify(auditLogRepository).save(argThat(log -> 
            log.getEventType().equals("ERROR") &&
            log.getStatus().equals("FAILED") &&
            log.getDetails().contains("Test exception")
        ));
    }
} 