ckage com.xcontent.repository;

import com.xcontent.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public interface TemplateAccessLogRepository extends MongoRepository<TemplateAccessLog, String>, TemplateAccessLogRepositoryCustom {
    long countByTemplateIdAndTimestampBetween(
        String templateId,
        Long startTime,
        Long endTime
    );

    @Query(value = "{ 'templateId': ?0, 'timestamp': { $gte: ?1, $lte: ?2 } }",
           group = { "_id": "$accessType", "count": { "$sum": 1 } })
    Map<AccessType, Long> countByAccessType(
        String templateId,
        Long startTime,
        Long endTime
    );

    @Query(value = "{ 'templateId': ?0, 'timestamp': { $gte: ?1, $lte: ?2 } }",
           group = { "_id": "$result", "count": { "$sum": 1 } })
    Map<AccessResult, Long> countByResult(
        String templateId,
        Long startTime,
        Long endTime
    );

    @Query(value = "{ 'templateId': ?0, 'timestamp': { $gte: ?1, $lte: ?2 } }",
           group = { "_id": { "$hour": "$timestamp" }, "count": { "$sum": 1 } })
    Map<String, Long> countByHour(
        String templateId,
        Long startTime,
        Long endTime
    );
} 
