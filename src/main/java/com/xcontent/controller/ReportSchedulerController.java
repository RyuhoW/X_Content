package com.xcontent.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.xcontent.model.ReportConfiguration;
import com.xcontent.service.ReportSchedulerService;
import org.springframework.stereotype.Component;

@Component
public class ReportSchedulerController {

    @FXML private TableView<ReportConfiguration> scheduleTable;
    @FXML private ComboBox<String> filterStatus;
    private final ReportSchedulerService schedulerService;
    private final ObservableList<ReportConfiguration> schedules = FXCollections.observableArrayList();

    public ReportSchedulerController(ReportSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @FXML
    public void initialize() {
        setupStatusFilter();
        setupTableColumns();
        loadSchedules();
    }

    private void setupStatusFilter() {
        filterStatus.setItems(FXCollections.observableArrayList(
            "すべて", "アクティブ", "一時停止", "完了"
        ));
        filterStatus.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> filterSchedules(newVal)
        );
    }

    @FXML
    private void handleAddSchedule() {
        showScheduleDialog(null);
    }

    private void loadSchedules() {
        schedules.clear();
        schedules.addAll(schedulerService.getAllSchedules());
        scheduleTable.setItems(schedules);
    }

    private void filterSchedules(String status) {
        if ("すべて".equals(status)) {
            loadSchedules();
            return;
        }
        schedules.clear();
        schedules.addAll(schedulerService.getSchedulesByStatus(status));
    }

    private void showScheduleDialog(ReportConfiguration schedule) {
        // スケジュール設定ダイアログを表示する実装
    }
} 