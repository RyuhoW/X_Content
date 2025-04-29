ckage com.xcontent.repository;

import com.xcontent.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TemplateAuditLogRepository extends MongoRepository<TemplateAuditLog, String>, TemplateAuditLogRepositoryCustom {
    List<TemplateAuditLog> findByTemplateId(String templateId);
    List<TemplateAuditLog> findByTemplateIdAndVersion(String templateId, String version);
    List<TemplateAuditLog> findByPerformedByAndTimestampBetween(
        String performedBy,
        Long startTime,}
        Long endTime
    );
} 
