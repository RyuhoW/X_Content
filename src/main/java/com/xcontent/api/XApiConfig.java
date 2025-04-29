package com.xcontent.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class XApiConfig {
    private static final Logger logger = LoggerFactory.getLogger(XApiConfig.class);
    private static final String CONFIG_FILE = "xapi.properties";
    
    private final String apiKey;
    private final String apiSecret;
    private final String baseUrl;
    private final int maxRetries;
    private final long retryDelayMs;

    public XApiConfig() {
        Properties props = loadProperties();
        this.apiKey = getRequiredProperty(props, "x.api.key");
        this.apiSecret = getRequiredProperty(props, "x.api.secret");
        this.baseUrl = props.getProperty("x.api.base.url", "https://api.twitter.com/2");
        this.maxRetries = Integer.parseInt(props.getProperty("x.api.max.retries", "3"));
        this.retryDelayMs = Long.parseLong(props.getProperty("x.api.retry.delay.ms", "1000"));
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException e) {
            logger.error("Failed to load X API configuration", e);
            throw new RuntimeException("Failed to load X API configuration", e);
        }
        return props;
    }

    private String getRequiredProperty(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Required property " + key + " is not set");
        }
        return value;
    }

    public String getApiKey() { return apiKey; }
    public String getApiSecret() { return apiSecret; }
    public String getBaseUrl() { return baseUrl; }
    public int getMaxRetries() { return maxRetries; }
    public long getRetryDelayMs() { return retryDelayMs; }
}
