CREATE TABLE IF NOT EXISTS templates (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    name TEXT NOT NULL,
    category TEXT NOT NULL,
    content TEXT NOT NULL,
    isDefault BOOLEAN NOT NULL DEFAULT 0,
    createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS contents (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    accountId INTEGER NOT NULL,
    text TEXT NOT NULL,
    hashtags TEXT,
    mediaUrls TEXT,
    templateId INTEGER,
    status TEXT NOT NULL DEFAULT 'draft',
    createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (templateId) REFERENCES templates(id)
);

CREATE INDEX idx_templates_userId ON templates(userId);
CREATE INDEX idx_contents_accountId ON contents(accountId);
CREATE INDEX idx_contents_templateId ON contents(templateId);
