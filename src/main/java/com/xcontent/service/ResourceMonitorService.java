package com.xcontent.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ResourceMonitorService {
    private final AtomicInteger activeGenerations = new AtomicInteger(0);
    private static final int MAX_CONCURRENT_GENERATIONS = 10;

    public boolean canStartNewGeneration() {
        return activeGenerations.get() < MAX_CONCURRENT_GENERATIONS;
    }

    public void reportGenerationStarted() {
        activeGenerations.incrementAndGet();
    }

    public void reportGenerationCompleted() {
        activeGenerations.decrementAndGet();
    }

    public int getActiveGenerationCount() {
        return activeGenerations.get();
    }

    public void cleanup() {
        // 一時ファイルの削除
        // キャッシュのクリーンアップ
        System.gc();
    }
} 