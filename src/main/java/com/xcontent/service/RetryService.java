package com.xcontent.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import com.xcontent.exception.ReportGenerationException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
public class RetryService {

    @Retryable(
        value = {ReportGenerationException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public <T> CompletableFuture<T> executeWithRetry(Supplier<CompletableFuture<T>> task) {
        return task.get();
    }

    @Recover
    public <T> CompletableFuture<T> recover(ReportGenerationException e, Supplier<CompletableFuture<T>> task) {
        // 最終的な失敗を記録
        LoggingService.logError("Report generation failed after retries", e);
        return CompletableFuture.failedFuture(e);
    }
} 