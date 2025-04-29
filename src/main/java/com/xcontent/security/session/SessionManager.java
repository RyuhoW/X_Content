package com.xcontent.security.session;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xcontent.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    private static final long EXPIRATION_TIME = 86400000; // 24時間

    private final Algorithm algorithm;

    public SessionManager() {
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalStateException("JWT_SECRET_KEY environment variable is not set");
        }
        this.algorithm = Algorithm.HMAC256(SECRET_KEY);
    }

    public String createSession(User user) {
        try {
            return JWT.create()
                    .withSubject(user.getId().toString())
                    .withClaim("username", user.getUsername())
                    .withClaim("email", user.getEmail())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(algorithm);
        } catch (Exception e) {
            logger.error("Error creating session token", e);
            throw new SecurityException("Error creating session token", e);
        }
    }

    public boolean validateSession(String token) {
        try {
            JWT.require(algorithm)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            logger.warn("Invalid session token: {}", e.getMessage());
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            logger.error("Error extracting user ID from token", e);
            throw new SecurityException("Invalid token", e);
        }
    }
}
