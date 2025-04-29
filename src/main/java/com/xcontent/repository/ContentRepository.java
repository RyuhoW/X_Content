package com.xcontent.repository;

import com.xcontent.model.Content;
import java.util.List;
import java.util.Optional;

public interface ContentRepository {
    Content save(Content content);
    Optional<Content> findById(Long id);
    List<Content> findAll();
    List<Content> findByUserId(Long userId);
    void update(Content content);
    void delete(Long id);
    List<Content> findByStatus(String status);
} 