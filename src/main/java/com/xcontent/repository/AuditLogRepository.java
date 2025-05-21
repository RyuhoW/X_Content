package com.xcontent.repository;

import com.xcontent.model.AuditLog;
import java.util.List;

public interface AuditLogRepository {
    AuditLog save(AuditLog log);
    List<AuditLog> findByResourceIdOrderByTimestampDesc(String resourceId);
    List<AuditLog> findByUserIdOrderByTimestampDesc(String userId);
} 