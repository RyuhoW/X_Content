package com.xcontent.service;

import com.xcontent.model.auth.License;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class LicenseService {
    private static final Logger logger = LogManager.getLogger(LicenseService.class);
    private static final Map<String, Integer> PLAN_LIMITS = new HashMap<>();

    static {
        PLAN_LIMITS.put("FREE", 1);      // 1 account
        PLAN_LIMITS.put("AMATEUR", 3);    // 3 accounts
        PLAN_LIMITS.put("PROFESSIONAL", 10); // 10 accounts
        PLAN_LIMITS.put("BLACK", -1);     // unlimited accounts
    }

    public boolean validateLicense(License license) {
        if (!license.isValid()) {
            logger.warn("License expired or inactive");
            return false;
        }
        return true;
    }

    public int getAccountLimit(String plan) {
        return PLAN_LIMITS.getOrDefault(plan.toUpperCase(), 0);
    }

    public boolean canAddAccount(License license, int currentAccountCount) {
        int limit = getAccountLimit(license.getPlan());
        return limit == -1 || currentAccountCount < limit;
    }

    public boolean hasFeatureAccess(License license, String feature) {
        // Feature access control based on license type
        switch (feature.toUpperCase()) {
            case "ANALYTICS":
                return !license.getPlan().equals("FREE");
            case "TEMPLATES":
                return !license.getPlan().equals("FREE");
            case "SCHEDULING":
                return !license.getPlan().equals("FREE");
            case "AI_OPTIMIZATION":
                return license.getPlan().equals("PROFESSIONAL") || 
                       license.getPlan().equals("BLACK");
            default:
                return true;
        }
    }
} 