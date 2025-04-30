package com.xcontent.service;

import com.xcontent.model.ReportConfiguration;
import com.xcontent.repository.ReportConfigurationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportSchedulerService {
    private final ReportConfigurationRepository configRepository;
    private final ReportGenerationService generationService;

    public ReportSchedulerService(
            ReportConfigurationRepository configRepository,
            ReportGenerationService generationService) {
        this.configRepository = configRepository;
        this.generationService = generationService;
    }

    @Scheduled(fixedRate = 60000) // 1分ごとにチェック
    public void processScheduledReports() {
        LocalDateTime now = LocalDateTime.now();
        List<ReportConfiguration> dueReports = configRepository.findDueReports(now);

        for (ReportConfiguration config : dueReports) {
            processReport(config);
        }
    }

    private void processReport(ReportConfiguration config) {
        generationService.generateReport(
                config.getTemplate(),
                config,
                config.getParameters()
        ).thenAccept(outputPath -> {
            // 次回の実行時間を更新
            updateNextRunTime(config);
        }).exceptionally(e -> {
            // エラー処理
            handleError(config, e);
            return null;
        });
    }

    private void updateNextRunTime(ReportConfiguration config) {
        LocalDateTime nextRun = calculateNextRunTime(config);
        config.setNextRunTime(nextRun);
        configRepository.save(config);
    }

    private LocalDateTime calculateNextRunTime(ReportConfiguration config) {
        // スケジュール式に基づいて次回実行時間を計算
        return switch (config.getScheduleType()) {
            case DAILY -> config.getNextRunTime().plusDays(1);
            case WEEKLY -> config.getNextRunTime().plusWeeks(1);
            case MONTHLY -> config.getNextRunTime().plusMonths(1);
            default -> null;
        };
    }

    private void handleError(ReportConfiguration config, Throwable e) {
        // エラーログの記録
        // 管理者への通知
        // リトライポリシーの適用
    }
} 