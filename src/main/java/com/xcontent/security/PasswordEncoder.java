package com.xcontent.security;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordEncoder {
    private static final Logger logger = LoggerFactory.getLogger(PasswordEncoder.class);
    private static final int SALT_ROUNDS = 12;

    public String encode(String rawPassword) {
        try {
            return BCrypt.hashpw(rawPassword, BCrypt.gensalt(SALT_ROUNDS));
        } catch (Exception e) {
            logger.error("Error encoding password", e);
            throw new SecurityException("Error encoding password", e);
        }
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        try {
            return BCrypt.checkpw(rawPassword, encodedPassword);
        } catch (Exception e) {
            logger.error("Error checking password", e);
            return false;
        }
    }
}
