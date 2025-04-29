package com.xcontent.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String encryptedPassword;
    
    private String xOauthToken;
    private String xOauthTokenSecret;
    
    @Column(nullable = false)
    private String licenseType;
    
    @Column(nullable = false)
    private String subscriptionPlan;
    
    @Column(nullable = false)
    private LocalDateTime subscriptionExpiryDate;
    
    private String stripeCustomerId;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
