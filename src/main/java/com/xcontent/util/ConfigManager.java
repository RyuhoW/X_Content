package com.xcontent.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final String CONFIG_FILE = "config.properties";
    private static ConfigManager instance;
    private final Properties properties;

    private ConfigManager() {
        properties = new Properties();
        loadConfig();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
            logger.info("Configuration loaded successfully");
        } catch (FileNotFoundException e) {
            createDefaultConfig();
        } catch (IOException e) {
            logger.error("Error loading configuration", e);
        }
    }

    private void createDefaultConfig() {
        properties.setProperty("database.url", "jdbc:sqlite:xcontent.db");
        properties.setProperty("api.base.url", "https://api.twitter.com/2");
        properties.setProperty("app.theme", "light");
        saveConfig();
    }

    public void saveConfig() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "XContent Configuration");
            logger.info("Configuration saved successfully");
        } catch (IOException e) {
            logger.error("Error saving configuration", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveConfig();
    }
} 