package com.xcontent.service.analytics;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.xcontent.model.analytics.AnalyticsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataCacheService {
    private static final Logger logger = LoggerFactory.getLogger(DataCacheService.class);
    
    private final Cache<String, AnalyticsData> dataCache;
    private final ConcurrentLinkedQueue<AnalyticsData> timeSeriesCache;
    private static final int MAX_TIME_SERIES_POINTS = 100;

    public DataCacheService() {
        this.dataCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(5))
                .maximumSize(1000)
                .build();
        
        this.timeSeriesCache = new ConcurrentLinkedQueue<>();
    }

    public void updateCache(AnalyticsData data) {
        try {
            dataCache.put(data.getId(), data);
            addToTimeSeriesCache(data);
        } catch (Exception e) {
            logger.error("Error updating cache", e);
        }
    }

    private void addToTimeSeriesCache(AnalyticsData data) {
        timeSeriesCache.add(data);
        while (timeSeriesCache.size() > MAX_TIME_SERIES_POINTS) {
            timeSeriesCache.poll();
        }
    }

    public AnalyticsData getLatestData(String id) {
        return dataCache.getIfPresent(id);
    }

    public ConcurrentLinkedQueue<AnalyticsData> getTimeSeriesData() {
        return new ConcurrentLinkedQueue<>(timeSeriesCache);
    }
}
