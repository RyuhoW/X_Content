package com.xcontent.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiter.class);
    
    private final ConcurrentHashMap<String, AtomicInteger> requestCounts;
    private final ConcurrentHashMap<String, Integer> limits;
    private final ScheduledExecutorService scheduler;

    public RateLimiter() {
        this.requestCounts = new ConcurrentHashMap<>();
        this.limits = new ConcurrentHashMap<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        // デフォルトのレート制限を設定
        setLimit("tweets", 50); // 15分あたり50ツイート
        setLimit("media", 25);  // 15分あたり25メディアアップロード

        // 15分ごとにカウンターをリセット
        scheduler.scheduleAtFixedRate(this::resetCounters, 15, 15, TimeUnit.MINUTES);
    }

    public void setLimit(String endpoint, int limit) {
        limits.put(endpoint, limit);
        requestCounts.putIfAbsent(endpoint, new AtomicInteger(0));
    }

    public boolean tryAcquire(String endpoint) {
        AtomicInteger counter = requestCounts.get(endpoint);
        Integer limit = limits.get(endpoint);

        if (counter == null || limit == null) {
            logger.warn("No rate limit configured for endpoint: {}", endpoint);
            return false;
        }

        int current = counter.get();
        if (current >= limit) {
            logger.warn("Rate limit exceeded for endpoint: {}", endpoint);
            return false;
        }

        return counter.incrementAndGet() <= limit;
    }

    public void resetCounters() {
        requestCounts.forEach((endpoint, counter) -> counter.set(0));
        logger.info("Rate limit counters reset");
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
