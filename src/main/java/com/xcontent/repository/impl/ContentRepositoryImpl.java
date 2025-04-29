package com.xcontent.repository.impl;

import com.xcontent.config.DatabaseConfig;
import com.xcontent.model.Content;
import com.xcontent.repository.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContentRepositoryImpl implements ContentRepository {
    private static final Logger logger = LoggerFactory.getLogger(ContentRepositoryImpl.class);

    @Override
    public Content save(Content content) {
        String sql = "INSERT INTO contents (user_id, account_id, content_text, media_urls, hashtags, status, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, content.getUserId());
            pstmt.setLong(2, content.getAccountId());
            pstmt.setString(3, content.getText());
            pstmt.setString(4, String.join(",", content.getMediaUrls()));
            pstmt.setString(5, String.join(",", content.getHashtags()));
            pstmt.setString(6, content.getStatus());
            pstmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating content failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    content.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating content failed, no ID obtained.");
                }
            }
            
            conn.commit();
            return content;
        } catch (SQLException e) {
            logger.error("Error saving content: {}", e.getMessage(), e);
            throw new RuntimeException("Error saving content", e);
        }
    }

    @Override
    public Optional<Content> findById(Long id) {
        String sql = "SELECT * FROM contents WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToContent(rs));
                }
            }
            
            return Optional.empty();
        } catch (SQLException e) {
            logger.error("Error finding content by id: {}", e.getMessage(), e);
            throw new RuntimeException("Error finding content by id", e);
        }
    }

    @Override
    public List<Content> findAll() {
        String sql = "SELECT * FROM contents";
        List<Content> contents = new ArrayList<>();
        
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                contents.add(mapResultSetToContent(rs));
            }
            
            return contents;
        } catch (SQLException e) {
            logger.error("Error finding all contents: {}", e.getMessage(), e);
            throw new RuntimeException("Error finding all contents", e);
        }
    }

    @Override
    public List<Content> findByUserId(Long userId) {
        String sql = "SELECT * FROM contents WHERE user_id = ?";
        List<Content> contents = new ArrayList<>();
        
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    contents.add(mapResultSetToContent(rs));
                }
            }
            
            return contents;
        } catch (SQLException e) {
            logger.error("Error finding contents by user id: {}", e.getMessage(), e);
            throw new RuntimeException("Error finding contents by user id", e);
        }
    }

    @Override
    public void update(Content content) {
        String sql = "UPDATE contents SET content_text = ?, media_urls = ?, hashtags = ?, status = ?, updated_at = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, content.getText());
            pstmt.setString(2, String.join(",", content.getMediaUrls()));
            pstmt.setString(3, String.join(",", content.getHashtags()));
            pstmt.setString(4, content.getStatus());
            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setLong(6, content.getId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Updating content failed, no rows affected.");
            }
            
            conn.commit();
        } catch (SQLException e) {
            logger.error("Error updating content: {}", e.getMessage(), e);
            throw new RuntimeException("Error updating content", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM contents WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Deleting content failed, no rows affected.");
            }
            
            conn.commit();
        } catch (SQLException e) {
            logger.error("Error deleting content: {}", e.getMessage(), e);
            throw new RuntimeException("Error deleting content", e);
        }
    }

    @Override
    public List<Content> findByStatus(String status) {
        String sql = "SELECT * FROM contents WHERE status = ?";
        List<Content> contents = new ArrayList<>();
        
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    contents.add(mapResultSetToContent(rs));
                }
            }
            
            return contents;
        } catch (SQLException e) {
            logger.error("Error finding contents by status: {}", e.getMessage(), e);
            throw new RuntimeException("Error finding contents by status", e);
        }
    }

    private Content mapResultSetToContent(ResultSet rs) throws SQLException {
        Content content = new Content();
        content.setId(rs.getLong("id"));
        content.setUserId(rs.getLong("user_id"));
        content.setAccountId(rs.getLong("account_id"));
        content.setText(rs.getString("content_text"));
        
        String mediaUrls = rs.getString("media_urls");
        if (mediaUrls != null && !mediaUrls.isEmpty()) {
            content.setMediaUrls(List.of(mediaUrls.split(",")));
        }
        
        String hashtags = rs.getString("hashtags");
        if (hashtags != null && !hashtags.isEmpty()) {
            content.setHashtags(List.of(hashtags.split(",")));
        }
        
        content.setStatus(rs.getString("status"));
        content.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        content.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        
        return content;
    }
} 