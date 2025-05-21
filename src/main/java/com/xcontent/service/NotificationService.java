package com.xcontent.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    public void sendReportNotification(String recipientEmail, String subject, String message) {
        // TODO: 実際のメール送信実装
        System.out.println("Sending notification to: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
    }
} 