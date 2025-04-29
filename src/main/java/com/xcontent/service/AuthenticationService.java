package com.xcontent.service;

import com.xcontent.model.User;
import com.xcontent.model.auth.UserCredentials;
import com.xcontent.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class AuthenticationService {
    private static final Logger logger = LogManager.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private User currentUser;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent() && BCrypt.checkpw(password, user.get().getEncryptedPassword())) {
                currentUser = user.get();
                logger.info("User authenticated successfully: {}", username);
                return true;
            }
            logger.warn("Authentication failed for user: {}", username);
            return false;
        } catch (Exception e) {
            logger.error("Authentication error", e);
            return false;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
        logger.info("User logged out");
    }
} 