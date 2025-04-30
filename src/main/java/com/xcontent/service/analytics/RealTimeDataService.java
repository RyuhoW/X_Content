package com.xcontent.service.analytics;

import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.function.Consumer;

public class RealTimeDataService {
    private static final Logger logger = LoggerFactory.getLogger(RealTimeDataService.class);
    
    private final ScheduledExecutorService scheduler;
    private final DataCacheService cacheService;
    private final ConcurrentMap<String, Consumer<AnalyticsData>> subscribers;
    
    public RealTimeDataService() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.cacheService = new DataCacheService();
        this.subscribers = new ConcurrentHashMap<>();
    }

    public void startRealTimeUpdates() {
        scheduler.scheduleAtFixedRate(
            this::fetchAndUpdateData,
            0,
            1,
            TimeUnit.SECONDS
        );
    }

    private void fetchAndUpdateData() {
        try {
            AnalyticsData data = fetchLatestData();
            cacheService.updateCache(data);
            notifySubscribers(data);
        } catch (Exception e) {
            logger.error("Error fetching real-time data", e);
        }
    }

    private AnalyticsData fetchLatestData() {
        // TODO: Implement actual data fetching from API
        return new AnalyticsData();
    }

    private void notifySubscribers(AnalyticsData data) {
        subscribers.values().forEach(subscriber ->
            Platform.runLater(() -> subscriber.accept(data))
        );
    }

    public void subscribe(String id, Consumer<AnalyticsData> subscriber) {
        subscribers.put(id, subscriber);
    }

    public void unsubscribe(String id) {
        subscribers.remove(id);
    }

    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
