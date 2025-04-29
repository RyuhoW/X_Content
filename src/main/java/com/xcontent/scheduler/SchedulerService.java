package com.xcontent.scheduler;

import com.xcontent.api.XApiClient;
import com.xcontent.model.Content;
import com.xcontent.model.Schedule;
import com.xcontent.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
    
    private final ScheduledExecutorService scheduler;
    private final XApiClient apiClient;
    private final NotificationService notificationService;
    private final AtomicBoolean isRunning;

    public SchedulerService(NotificationService notificationService) {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.apiClient = new XApiClient();
        this.notificationService = notificationService;
        this.isRunning = new AtomicBoolean(false);
    }

    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            scheduler.scheduleAtFixedRate(
                this::processScheduledPosts,
                0,
                1,
                TimeUnit.MINUTES
            );
            logger.info("Scheduler service started");
        }
    }

    public void stop() {
        if (isRunning.compareAndSet(true, false)) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            logger.info("Scheduler service stopped");
        }
    }

    private void processScheduledPosts() {
        try {
            LocalDateTime now = LocalDateTime.now();
            // TODO: Implement repository call to get pending schedules
            // List<Schedule> pendingSchedules = scheduleRepository.findPendingSchedules(now);

            // for (Schedule schedule : pendingSchedules) {
            //     processSchedule(schedule);
            // }
        } catch (Exception e) {
            logger.error("Error processing scheduled posts", e);
            notificationService.sendNotification(
                "Scheduler Error",
                "Failed to process scheduled posts: " + e.getMessage(),
                "error"
            );
        }
    }

    private void processSchedule(Schedule schedule) {
        try {
            // TODO: Implement content repository call
            // Content content = contentRepository.findById(schedule.getContentId());
            
            // apiClient.postTweet(content.getText(), content.getMediaIds())
            //     .thenAccept(tweet -> {
            //         schedule.setStatus("completed");
            //         scheduleRepository.update(schedule);
            //         notificationService.sendNotification(
            //             "Post Scheduled",
            //             "Successfully posted scheduled content",
            //             "info"
            //         );
            //     })
            //     .exceptionally(e -> {
            //         handleScheduleError(schedule, e);
            //         return null;
            //     });
        } catch (Exception e) {
            handleScheduleError(schedule, e);
        }
    }

    private void handleScheduleError(Schedule schedule, Throwable e) {
        logger.error("Error processing schedule {}", schedule.getId(), e);
        schedule.setStatus("failed");
        schedule.setFailureReason(e.getMessage());
        // scheduleRepository.update(schedule);
        
        notificationService.sendNotification(
            "Schedule Failed",
            "Failed to post scheduled content: " + e.getMessage(),
            "error"
        );
    }
}
