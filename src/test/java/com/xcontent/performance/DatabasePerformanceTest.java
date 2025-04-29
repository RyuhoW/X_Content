package com.xcontent.performance;

import com.xcontent.config.DatabaseConfig;
import com.xcontent.model.Content;
import com.xcontent.repository.impl.ContentRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("performance")
public class DatabasePerformanceTest {

    private ContentRepositoryImpl contentRepository;
    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        DatabaseConfig.initialize();
        contentRepository = new ContentRepositoryImpl();
        executorService = Executors.newFixedThreadPool(10);
    }

    @Test
    void bulkInsert_ShouldCompleteWithinTimeLimit() {
        // Arrange
        int recordCount = 1000;
        List<Content> contents = generateTestContents(recordCount);
        Duration timeLimit = Duration.ofSeconds(5);

        // Act
        Instant start = Instant.now();
        
        List<CompletableFuture<Void>> futures = contents.stream()
            .map(content -> CompletableFuture.runAsync(() ->
                contentRepository.save(content), executorService))
            .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        
        Duration duration = Duration.between(start, Instant.now());

        // Assert
        assertTrue(duration.compareTo(timeLimit) < 0,
            "Bulk insert took too long: " + duration.toMillis() + "ms");
    }

    @Test
    void concurrentReads_ShouldHandleLoad() {
        // Arrange
        int concurrentUsers = 50;
        int requestsPerUser = 20;
        Duration timeLimit = Duration.ofSeconds(10);

        // Act
        Instant start = Instant.now();
        
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < concurrentUsers; i++) {
            futures.add(CompletableFuture.runAsync(() -> {
                for (int j = 0; j < requestsPerUser; j++) {
                    contentRepository.findAll();
                }
            }, executorService));
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        
        Duration duration = Duration.between(start, Instant.now());

        // Assert
        assertTrue(duration.compareTo(timeLimit) < 0,
            "Concurrent reads took too long: " + duration.toMillis() + "ms");
    }

    private List<Content> generateTestContents(int count) {
        return IntStream.range(0, count)
            .mapToObj(i -> {
                Content content = new Content();
                content.setText("Test content " + i);
                return content;
            })
            .toList();
    }
}
