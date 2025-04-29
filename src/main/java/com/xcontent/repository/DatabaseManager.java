package com.xcontent.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseManager {
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);
    private static final String DB_URL = "jdbc:sqlite:xcontent.db";
    private static DatabaseManager instance;

    private DatabaseManager() {
        initializeDatabase();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void initializeDatabase() {
        try {
            // Create database directory if it doesn't exist
            Files.createDirectories(Paths.get("db"));
            
            // Execute schema.sql
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                String schema = new String(Files.readAllBytes(
                    Paths.get("src/main/resources/db/schema.sql")));
                stmt.executeUpdate(schema);
                logger.info("Database schema initialized successfully");
            }
        } catch (Exception e) {
            logger.error("Failed to initialize database", e);
            throw new RuntimeException("Database initialization failed", e);
        }
    }
} 