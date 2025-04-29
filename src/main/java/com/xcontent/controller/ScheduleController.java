package com.xcontent.controller;

import com.xcontent.model.Schedule;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);

    @FXML private ToggleButton dayViewButton;
    @FXML private ToggleButton weekViewButton;
    @FXML private ToggleButton monthViewButton;
    @FXML private VBox calendarContainer;
    @FXML private TableView<Schedule> scheduleTable;
    @FXML private TableColumn<Schedule, LocalDateTime> timeColumn;
    @FXML private TableColumn<Schedule, String> contentColumn;
    @FXML private TableColumn<Schedule, String> statusColumn;
    @FXML private TableColumn<Schedule, Void> actionsColumn;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupToggleButtons();
        setupTableColumns();
        loadSchedules();
    }

    private void setupToggleButtons() {
        ToggleGroup viewGroup = new ToggleGroup();
        dayViewButton.setToggleGroup(viewGroup);
        weekViewButton.setToggleGroup(viewGroup);
        monthViewButton.setToggleGroup(viewGroup);

        viewGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                oldVal.setSelected(true);
            } else {
                updateCalendarView();
            }
        });
    }

    private void setupTableColumns() {
        timeColumn.setCellValueFactory(data -> 
            new SimpleObjectProperty<>(data.getValue().getScheduledTime()));
        
        timeColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime time, boolean empty) {
                super.updateItem(time, empty);
                if (empty || time == null) {
                    setText(null);
                } else {
                    setText(timeFormatter.format(time));
                }
            }
        });

        statusColumn.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getScheduledStatus()));

        contentColumn.setCellValueFactory(data ->
            new SimpleStringProperty("Content Preview")); // 一時的な実装

        setupActionsColumn();
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(e -> handleEdit(getTableRow().getItem()));
                deleteButton.setOnAction(e -> handleDelete(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    var box = new javafx.scene.layout.HBox(5);
                    box.getChildren().addAll(editButton, deleteButton);
                    setGraphic(box);
                }
            }
        });
    }

    private void loadSchedules() {
        // TODO: Load schedules from database
        logger.info("Loading schedules");
    }

    private void updateCalendarView() {
        // TODO: Update calendar view based on selected view mode
        logger.info("Updating calendar view");
    }

    @FXML
    private void handleNewSchedule() {
        logger.info("Creating new schedule");
        // TODO: Show new schedule dialog
    }

    private void handleEdit(Schedule schedule) {
        if (schedule != null) {
            logger.info("Editing schedule: {}", schedule.getScheduleId());
            // TODO: Show edit dialog
        }
    }

    private void handleDelete(Schedule schedule) {
        if (schedule != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Delete Schedule");
            confirm.setContentText("Are you sure you want to delete this schedule?");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    logger.info("Deleting schedule: {}", schedule.getScheduleId());
                    // TODO: Delete schedule from database
                }
            });
        }
    }
} 