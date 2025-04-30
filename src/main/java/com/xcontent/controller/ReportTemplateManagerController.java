package com.xcontent.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.xcontent.model.ReportTemplate;
import com.xcontent.service.ReportTemplateService;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ReportTemplateManagerController {
    
    @FXML private TableView<ReportTemplate> templateTable;
    @FXML private TableColumn<ReportTemplate, String> nameColumn;
    @FXML private TableColumn<ReportTemplate, String> descriptionColumn;
    @FXML private TableColumn<ReportTemplate, String> formatColumn;
    @FXML private TableColumn<ReportTemplate, LocalDateTime> createdAtColumn;
    @FXML private TableColumn<ReportTemplate, Void> actionsColumn;
    @FXML private TextField searchField;

    private final ReportTemplateService templateService;
    private final ObservableList<ReportTemplate> templates = FXCollections.observableArrayList();

    public ReportTemplateManagerController(ReportTemplateService templateService) {
        this.templateService = templateService;
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        loadTemplates();
        setupSearch();
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
        formatColumn.setCellValueFactory(data -> data.getValue().formatProperty());
        createdAtColumn.setCellValueFactory(data -> data.getValue().createdAtProperty());
        
        setupActionsColumn();
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("編集");
            private final Button deleteButton = new Button("削除");
            private final HBox buttons = new HBox(5, editButton, deleteButton);

            {
                editButton.setOnAction(e -> handleEdit(getTableRow().getItem()));
                deleteButton.setOnAction(e -> handleDelete(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttons);
            }
        });
    }

    @FXML
    private void handleAddTemplate() {
        // テンプレート追加ダイアログを表示
        showTemplateDialog(null);
    }

    @FXML
    private void handleImportTemplate() {
        // テンプレートインポートダイアログを表示
    }

    private void handleEdit(ReportTemplate template) {
        // テンプレート編集ダイアログを表示
        showTemplateDialog(template);
    }

    private void handleDelete(ReportTemplate template) {
        // 削除確認ダイアログを表示
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("テンプレート削除");
        alert.setHeaderText("テンプレートを削除しますか？");
        alert.setContentText("この操作は取り消せません。");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                templateService.deleteTemplate(template.getId());
                loadTemplates();
            }
        });
    }

    private void loadTemplates() {
        templates.clear();
        templates.addAll(templateService.getAllTemplates());
        templateTable.setItems(templates);
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                loadTemplates();
            } else {
                templates.clear();
                templates.addAll(templateService.searchTemplates(newValue));
            }
        });
    }

    private void showTemplateDialog(ReportTemplate template) {
        // テンプレート編集ダイアログを表示する実装
    }
} 