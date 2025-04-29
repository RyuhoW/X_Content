package com.xcontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;

/**
 * Repository interface for Template Change operations
 */
@Repository
public interface TemplateChangeRepository extends JpaRepository<Object, Long> {
    
    /**
     * Find changes by template ID
     * @param templateId the template ID
     * @return list of changes
     */
    @Query("SELECT c FROM Change c WHERE c.templateId = :templateId ORDER BY c.timestamp DESC")
    List<Object> findByTemplateId(@Param("templateId") Long templateId);
    
    /**
     * Find changes by date range
     * @param startDate the start date
     * @param endDate the end date
     * @return list of changes
     */
    @Query("SELECT c FROM Change c WHERE c.timestamp BETWEEN :startDate AND :endDate ORDER BY c.timestamp DESC")
    List<Object> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
