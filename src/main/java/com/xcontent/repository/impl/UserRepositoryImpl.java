package com.xcontent.repository.impl;

import com.xcontent.model.User;
import com.xcontent.repository.UserRepository;
import com.xcontent.util.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);
    private final DatabaseManager dbManager;

    public UserRepositoryImpl() {
        this.dbManager = DatabaseManager.getInstance();
    }

    @Override
    public User save(User user) {
        // 一時的な実装：メモリ上での操作のみ
        logger.info("Saving user: {}", user.getUsername());
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        // 一時的な実装
        logger.info("Finding user by ID: {}", id);
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        // 一時的な実装
        logger.info("Finding user by username: {}", username);
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        // 一時的な実装
        logger.info("Finding user by email: {}", email);
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        // 一時的な実装
        logger.info("Finding all users");
        return new ArrayList<>();
    }

    @Override
    public void delete(Long id) {
        // 一時的な実装
        logger.info("Deleting user with ID: {}", id);
    }

    @Override
    public boolean exists(String username) {
        // 一時的な実装
        logger.info("Checking if user exists: {}", username);
        return false;
    }
} 