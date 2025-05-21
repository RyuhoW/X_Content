package com.xcontent.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@Entity
@Table(name = "report_templates")
public class ReportTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "template_content", nullable = false)
    private String templateContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "template_path")
    private String templatePath;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "is_public")
    private boolean isPublic;

    @ManyToMany
    @JoinTable(
        name = "report_template_shared_users",
        joinColumns = @JoinColumn(name = "report_template_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> sharedUsers;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StringProperty formatProperty() {
        return new SimpleStringProperty(templatePath);
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return new SimpleObjectProperty<>(createdAt);
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Set<User> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(Set<User> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }
} 