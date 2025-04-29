package com.xcontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;

/**
 * Repository interface for Template Access Log operations
 */
@Repository
public interface TemplateAccessLogRepository extends JpaRepository<Object, Long> {
    
    /**
     * Find access logs by template ID
     * @param templateId the template ID
     * @return list of access logs
     */
    @Query("SELECT l FROM AccessLog l WHERE l.templateId = :templateId ORDER BY l.timestamp DESC")
    List<Object> findByTemplateId(@Param("templateId") Long templateId);
    
    /**
     * Find access logs by user
     * @param userId the user ID
     * @return list of access logs
     */
    @Query("SELECT l FROM AccessLog l WHERE l.userId = :userId ORDER BY l.timestamp DESC")
    List<Object> findByUserId(@Param("userId") String userId);
}
