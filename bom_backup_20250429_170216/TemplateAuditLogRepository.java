package com.xcontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Template Audit Log operations
 */
@Repository
public interface TemplateAuditLogRepository extends JpaRepository<Object, Long> {
    
    /**
     * Find audit logs by template ID
     * @param templateId the template ID
     * @return list of audit logs
     */
    @Query("SELECT l FROM AuditLog l WHERE l.templateId = :templateId ORDER BY l.timestamp DESC")
    List<Object> findByTemplateId(@Param("templateId") Long templateId);
}
