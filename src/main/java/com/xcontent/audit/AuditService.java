package com.xcontent.audit;

import org.springframework.stereotype.Service;
import com.xcontent.model.AuditLog;
import com.xcontent.repository.AuditLogRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {
    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logAccess(String userId, String resourceId, String action) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setResourceId(resourceId);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setIpAddress(getCurrentIpAddress());
        
        auditLogRepository.save(log);
    }

    public List<AuditLog> getAuditTrail(String resourceId) {
        return auditLogRepository.findByResourceIdOrderByTimestampDesc(resourceId);
    }

    public List<AuditLog> getUserActivity(String userId) {
        return auditLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    private String getCurrentIpAddress() {
        // 現在のリクエストからIPアドレスを取得する実装
        return "127.0.0.1";
    }
} 