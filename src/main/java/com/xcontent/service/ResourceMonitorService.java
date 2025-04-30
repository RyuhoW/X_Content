package com.xcontent.service;

import org.springframework.stereotype.Service;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ResourceMonitorService {
    private final AtomicInteger activeReportGenerations = new AtomicInteger(0);
    private final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

    public boolean canStartNewGeneration() {
        // メモリ使用率とアクティブな生成数をチェック
        long usedMemory = memoryBean.getHeapMemoryUsage().getUsed();
        long maxMemory = memoryBean.getHeapMemoryUsage().getMax();
        
        return activeReportGenerations.get() < 10 && 
               (usedMemory / (double) maxMemory) < 0.8;
    }

    public void reportGenerationStarted() {
        activeReportGenerations.incrementAndGet();
    }

    public void reportGenerationCompleted() {
        activeReportGenerations.decrementAndGet();
    }

    public void cleanup() {
        // 一時ファイルの削除
        // キャッシュのクリーンアップ
        System.gc();
    }
} 