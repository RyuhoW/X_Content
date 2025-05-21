package com.xcontent.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "report_configurations")
public class ReportConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private ReportTemplate template;

    @Column(name = "schedule_type")
    private String scheduleType;

    @Column(name = "next_run_time")
    private LocalDateTime nextRunTime;

    @ElementCollection
    @CollectionTable(name = "report_parameters")
    private Map<String, Object> parameters;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ReportTemplate template) {
        this.template = template;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public LocalDateTime getNextRunTime() {
        return nextRunTime;
    }

    public void setNextRunTime(LocalDateTime nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    public String getTemplateName() {
        return template.getName();
    }

    public void setTemplateName(String templateName) {
        template.setName(templateName);
    }

    public String getOutputFormat() {
        return (String) parameters.get("outputFormat");
    }

    public void setOutputFormat(String outputFormat) {
        parameters.put("outputFormat", outputFormat);
    }

    public Map<String, Object> getData() {
        return parameters;
    }

    public void setData(Map<String, Object> data) {
        parameters.putAll(data);
    }

    public LocalDateTime getScheduledTime() {
        return nextRunTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.nextRunTime = scheduledTime;
    }

    public String getRecipientEmail() {
        return (String) parameters.get("recipientEmail");
    }

    public void setRecipientEmail(String recipientEmail) {
        parameters.put("recipientEmail", recipientEmail);
    }

    public boolean isRecurring() {
        return "recurring".equals(scheduleType);
    }

    public void setRecurring(boolean recurring) {
        scheduleType = recurring ? "recurring" : "one-time";
    }

    public String getRecurringSchedule() {
        return (String) parameters.get("recurringSchedule");
    }

    public void setRecurringSchedule(String recurringSchedule) {
        parameters.put("recurringSchedule", recurringSchedule);
    }
} 