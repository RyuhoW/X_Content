package com.xcontent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
 * Repository interface for Template Analytics operations
 */
@Repository
public interface TemplateAnalyticsRepository extends JpaRepository<Object, Long> {
    
    /**
     * Count accesses by template ID grouped by date
     * @param templateId the template ID
     * @return access count by date
     */
    @Query("SELECT DATE(a.timestamp) as date, COUNT(a) as count FROM Access a WHERE a.templateId = :templateId GROUP BY DATE(a.timestamp)")
    List<Map<String, Object>> countAccessesByDate(@Param("templateId") Long templateId);
    
    /**
     * Calculate average processing time by template ID
     * @param templateId the template ID
     * @return average processing time
     */
    @Query("SELECT AVG(a.processingTime) FROM Access a WHERE a.templateId = :templateId")
    Double calculateAverageProcessingTime(@Param("templateId") Long templateId);
    
    /**
     * Find most accessed templates
     * @param limit maximum number of results
     * @return list of templates with access count
     */
    @Query(value = "SELECT t.id, t.name, COUNT(a.id) as accessCount FROM templates t JOIN accesses a ON t.id = a.template_id GROUP BY t.id, t.name ORDER BY accessCount DESC LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostAccessedTemplates(@Param("limit") int limit);
    
    /**
     * Count template usage by user
     * @param userId the user ID
     * @return usage count by template
     */
    @Query("SELECT t.id, t.name, COUNT(a) as count FROM Template t JOIN Access a ON t.id = a.templateId WHERE a.userId = :userId GROUP BY t.id, t.name ORDER BY count DESC")
    List<Object[]> countTemplateUsageByUser(@Param("userId") String userId);
}
