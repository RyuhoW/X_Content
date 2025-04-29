package com.xcontent.model.auth;

import java.time.LocalDateTime;

public class License {
    private String type;
    private String plan;
    private LocalDateTime expiryDate;
    private String stripeCustomerId;
    private boolean isActive;

    // Constructors, getters, and setters
    public License(String type, String plan, LocalDateTime expiryDate) {
        this.type = type;
        this.plan = plan;
        this.expiryDate = expiryDate;
        this.isActive = true;
    }

    public boolean isValid() {
        return isActive && LocalDateTime.now().isBefore(expiryDate);
    }

    // Getters and setters
} 