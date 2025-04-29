package com.xcontent.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final String DB_FILE = "xcontent.db";
    private static HikariDataSource dataSource;

    public static void initialize() {
        try {
            setupDataSource();
            runMigrations();
            logger.info("Database initialization completed successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize database", e);
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    private static void setupDataSource() {
        HikariConfig config = new HikariConfig();
        String dbPath = getDbPath();
        logger.info("Setting up database at: {}", dbPath);
        
        config.setJdbcUrl("jdbc:sqlite:" + dbPath);
        config.setDriverClassName("org.sqlite.JDBC");
        
        // SQLite特有の設定
        config.setMaximumPoolSize(1); // SQLiteは同時接続に制限があるため
        config.setMinimumIdle(1);
        config.setAutoCommit(false);
        
        // コネクションプールの設定
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    private static void runMigrations() {
        logger.info("Running database migrations");
        Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .load();
        
        flyway.migrate();
    }

    private static String getDbPath() {
        String userHome = System.getProperty("user.home");
        Path dbDir = Paths.get(userHome, ".xcontent");
        if (!dbDir.toFile().exists()) {
            dbDir.toFile().mkdirs();
        }
        return dbDir.resolve(DB_FILE).toString();
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            initialize();
        }
        return dataSource;
    }

    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("Database connection pool has been shut down");
        }
    }
} 