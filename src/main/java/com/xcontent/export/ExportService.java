package com.xcontent.export;

import com.xcontent.analytics.PerformanceData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ExportService {
    private static final Logger logger = LoggerFactory.getLogger(ExportService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void exportToExcel(PerformanceData data, String filePath) {
        logger.info("Exporting performance data to Excel: {}", filePath);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Performance Data");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            createHeaderCell(headerRow, 0, "Date/Time");
            createHeaderCell(headerRow, 1, "Impressions");
            createHeaderCell(headerRow, 2, "Likes");
            createHeaderCell(headerRow, 3, "Retweets");
            createHeaderCell(headerRow, 4, "Replies");
            createHeaderCell(headerRow, 5, "Engagement Rate");

            // Fill data rows
            int rowNum = 1;
            for (var metrics : data.getMetrics()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(metrics.getTimestamp().format(DATE_FORMATTER));
                row.createCell(1).setCellValue(metrics.getImpressions());
                row.createCell(2).setCellValue(metrics.getLikes());
                row.createCell(3).setCellValue(metrics.getRetweets());
                row.createCell(4).setCellValue(metrics.getReplies());
                row.createCell(5).setCellValue(String.format("%.2f%%", metrics.getEngagementRate()));
            }

            // Add summary row
            Row summaryRow = sheet.createRow(rowNum + 1);
            summaryRow.createCell(0).setCellValue("Summary");
            summaryRow.createCell(1).setCellValue(data.getTotalImpressions());
            summaryRow.createCell(5).setCellValue(
                String.format("%.2f%%", data.getAverageEngagementRate()));

            // Auto-size columns
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            logger.info("Successfully exported data to {}", filePath);
        } catch (IOException e) {
            logger.error("Error exporting data to Excel", e);
            throw new RuntimeException("Failed to export data", e);
        }
    }

    private void createHeaderCell(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        // Add header styling if needed
    }
}
