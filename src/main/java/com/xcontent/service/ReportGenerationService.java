package com.xcontent.service;

import com.xcontent.model.ReportConfiguration;
import com.xcontent.model.ReportTemplate;
import com.xcontent.repository.ReportGenerationHistoryRepository;
import org.renjin.script.RenjinScriptEngine;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportGenerationService {
    private final RenjinScriptEngine rEngine;
    private final ReportGenerationHistoryRepository historyRepository;
    private final NotificationService notificationService;

    public ReportGenerationService(
            RenjinScriptEngine rEngine,
            ReportGenerationHistoryRepository historyRepository,
            NotificationService notificationService) {
        this.rEngine = rEngine;
        this.historyRepository = historyRepository;
        this.notificationService = notificationService;
    }

    public CompletableFuture<Path> generateReport(
            ReportTemplate template,
            ReportConfiguration config,
            Map<String, Object> parameters) {
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // レポート生成開始を記録
                var history = historyRepository.createHistory(config);

                // Rスクリプトの実行
                rEngine.put("template_path", template.getTemplatePath());
                rEngine.put("parameters", parameters);
                rEngine.put("output_format", config.getOutputFormat());
                
                Path outputPath = (Path) rEngine.eval("""
                    source('r_analytics/src/report_generator.R')
                    generate_report(template_path, parameters, output_format)
                """);

                // 生成成功を記録
                historyRepository.markSuccess(history.getId(), outputPath);

                // 通知送信
                if (config.getRecipientEmail() != null) {
                    notificationService.sendReportNotification(
                        config.getRecipientEmail(),
                        "レポート生成完了",
                        "レポートが正常に生成されました。"
                    );
                }

                return outputPath;

            } catch (Exception e) {
                // エラーを記録
                historyRepository.markError(config.getId(), e.getMessage());
                throw new ReportGenerationException("レポート生成に失敗しました", e);
            }
        });
    }
} 