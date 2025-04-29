package com.xcontent.repository;

import com.xcontent.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

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

@Repository
public interface TemplateChangeRepository extends MongoRepository<TemplateChange, String>, TemplateChangeRepositoryCustom {
    List<TemplateChange> findByTemplateIdAndVersionOrderByChangedAtDesc(
        String templateId,
        String version
    );
    List<TemplateChange> findByChangedByAndChangedAtBetween(
        String changedBy,
        Long startTime,
        Long endTime
    );
}

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

