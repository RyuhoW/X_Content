package com.xcontent.service;

import com.xcontent.model.Schedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class TaskScheduler {
    private static final Logger logger = LogManager.getLogger(TaskScheduler.class);
    private final ScheduledExecutorService executor;
    private final ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasks;

    public TaskScheduler() {
        this.executor = Executors.newScheduledThreadPool(1);
        this.scheduledTasks = new ConcurrentHashMap<>();
    }

    public void scheduleTask(Schedule schedule) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime scheduledTime = schedule.getScheduledTime();
        
        if (scheduledTime.isBefore(now)) {
            logger.warn("Cannot schedule task in the past: {}", schedule.getScheduleId());
            return;
        }

        long delay = java.time.Duration.between(now, scheduledTime).toSeconds();
        
        ScheduledFuture<?> future = executor.schedule(() -> {
            executeTask(schedule);
            if (schedule.isRecurring()) {
                rescheduleTask(schedule);
            }
        }, delay, TimeUnit.SECONDS);

        scheduledTasks.put(schedule.getScheduleId(), future);
        logger.info("Task scheduled: {}", schedule.getScheduleId());
    }

    public void cancelTask(Long scheduleId) {
        ScheduledFuture<?> future = scheduledTasks.remove(scheduleId);
        if (future != null) {
            future.cancel(false);
            logger.info("Task cancelled: {}", scheduleId);
        }
    }

    private void executeTask(Schedule schedule) {
        logger.info("Executing scheduled task: {}", schedule.getScheduleId());
        // TODO: Implement actual task execution
    }

    private void rescheduleTask(Schedule schedule) {
        // TODO: Implement recurring schedule logic
        logger.info("Rescheduling task: {}", schedule.getScheduleId());
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
} 