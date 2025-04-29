package com.xcontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;

/**
 * Repository interface for Template Audit operations
 */
@Repository
public interface TemplateAuditRepository extends JpaRepository<Object, Long> {
    
    /**
     * Find audit records by template ID
     * @param templateId the template ID
     * @return list of audit records
     */
    @Query("SELECT a FROM Audit a WHERE a.templateId = :templateId ORDER BY a.timestamp DESC")
    List<Object> findByTemplateId(@Param("templateId") Long templateId);
    
    /**
     * Find audit records by date range
     * @param startDate the start date
     * @param endDate the end date
     * @return list of audit records
     */
    @Query("SELECT a FROM Audit a WHERE a.timestamp BETWEEN :startDate AND :endDate ORDER BY a.timestamp DESC")
    List<Object> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    /**
     * Find audit records by user
     * @param userId the user ID
     * @return list of audit records
     */
    @Query("SELECT a FROM Audit a WHERE a.userId = :userId ORDER BY a.timestamp DESC")
    List<Object> findByUserId(@Param("userId") String userId);
}
