package com.xcontent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.xcontent.model.AuditLog;
import com.xcontent.repository.AuditLogRepository;
import java.time.LocalDateTime;

@Service
public class LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);
    private final AuditLogRepository auditLogRepository;

    public LoggingService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logReportGeneration(String reportId, String status, String details) {
        AuditLog log = new AuditLog();
        log.setEventType("REPORT_GENERATION");
        log.setResourceId(reportId);
        log.setStatus(status);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        
        auditLogRepository.save(log);
        logger.info("Report generation event: {} - {}", reportId, status);
    }

    public static void logError(String message, Throwable error) {
        logger.error(message, error);
    }

    private String getStackTraceAsString(Throwable error) {
        // スタックトレースを文字列に変換
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : error.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
} 