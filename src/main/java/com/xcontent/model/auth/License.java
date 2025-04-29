package com.xcontent.model.auth;

import java.time.LocalDateTime;

public class License {
    private String type;
    private String plan;
    private LocalDateTime expiryDate;
    private String stripeCustomerId;
    private boolean isActive;

    // Constructors
    public License() {
        this.isActive = true;
    }

    public License(String type, String plan, LocalDateTime expiryDate) {
        this();
        this.type = type;
        this.plan = plan;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Business logic
    public boolean isValid() {
        return isActive && LocalDateTime.now().isBefore(expiryDate);
    }

    public boolean isExpired() {
        return !isActive || LocalDateTime.now().isAfter(expiryDate);
    }

    public long getDaysUntilExpiry() {
        if (expiryDate == null) {
            return 0;
        }
        return java.time.Duration.between(LocalDateTime.now(), expiryDate).toDays();
    }
} 