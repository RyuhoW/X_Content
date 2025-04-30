-- レポートテンプレート管理テーブル
CREATE TABLE IF NOT EXISTS report_templates (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    template_path VARCHAR(255) NOT NULL,
    format VARCHAR(20) NOT NULL, -- HTML, PDF, DOCX等
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    is_active BOOLEAN DEFAULT true,
    version INTEGER DEFAULT 1,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- レポート設定管理テーブル
CREATE TABLE IF NOT EXISTS report_configurations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    template_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    schedule_type VARCHAR(20) NOT NULL, -- ONCE, DAILY, WEEKLY, MONTHLY
    schedule_expression VARCHAR(100), -- cronスタイルの実行スケジュール
    next_run_time TIMESTAMP,
    recipient_email VARCHAR(255),
    parameters TEXT, -- JSON形式でパラメータを保存
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    is_active BOOLEAN DEFAULT true,
    FOREIGN KEY (template_id) REFERENCES report_templates(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- レポート生成履歴テーブル
CREATE TABLE IF NOT EXISTS report_generation_history (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    configuration_id INTEGER NOT NULL,
    template_id INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL, -- SUCCESS, FAILED, IN_PROGRESS
    output_path VARCHAR(255),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    error_message TEXT,
    FOREIGN KEY (configuration_id) REFERENCES report_configurations(id),
    FOREIGN KEY (template_id) REFERENCES report_templates(id)
);

-- インデックス
CREATE INDEX idx_report_templates_name ON report_templates(name);
CREATE INDEX idx_report_configurations_template_id ON report_configurations(template_id);
CREATE INDEX idx_report_configurations_next_run_time ON report_configurations(next_run_time);
CREATE INDEX idx_report_generation_history_configuration_id ON report_generation_history(configuration_id); 