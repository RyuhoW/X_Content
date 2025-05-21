package com.xcontent.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceMonitorServiceTest {

    private ResourceMonitorService monitorService;

    @BeforeEach
    void setUp() {
        monitorService = new ResourceMonitorService();
    }

    @Test
    void canStartNewGeneration_WhenResourcesAvailable_ShouldReturnTrue() {
        // Act & Assert
        assertTrue(monitorService.canStartNewGeneration());
    }

    @Test
    void activeGenerations_ShouldTrackCorrectly() {
        // Arrange
        int initialCount = 0;

        // Act
        monitorService.reportGenerationStarted();
        monitorService.reportGenerationStarted();
        monitorService.reportGenerationCompleted();

        // Assert
        assertEquals(initialCount + 1, monitorService.getActiveGenerationCount());
    }

    @Test
    void canStartNewGeneration_WhenTooManyActive_ShouldReturnFalse() {
        // Arrange
        for (int i = 0; i < 10; i++) {
            monitorService.reportGenerationStarted();
        }

        // Act & Assert
        assertFalse(monitorService.canStartNewGeneration());
    }
} 