package com.xcontent.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseConfig {
    private static final String DB_FILE = "xcontent.db";
    private static HikariDataSource dataSource;

    public static void initialize() {
        setupDataSource();
        runMigrations();
    }

    private static void setupDataSource() {
        HikariConfig config = new HikariConfig();
        String dbPath = getDbPath();
        
        config.setJdbcUrl("jdbc:sqlite:" + dbPath);
        config.setDriverClassName("org.sqlite.JDBC");
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setAutoCommit(false);
        
        // SQLite specific settings
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    private static void runMigrations() {
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
        }
    }
}
