package com.xcontent.security;

import com.xcontent.model.User;
import com.xcontent.repository.UserRepository;
import com.xcontent.security.oauth.XOAuthService;
import com.xcontent.security.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;
    private final XOAuthService xOAuthService;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new PasswordEncoder();
        this.sessionManager = new SessionManager();
        this.xOAuthService = new XOAuthService();
    }

    public String authenticateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPasswordHash())) {
            logger.warn("Authentication failed for username: {}", username);
            throw new SecurityException("Invalid username or password");
        }

        return sessionManager.createSession(userOpt.get());
    }

    public String registerUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new SecurityException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new SecurityException("Email already exists");
        }

        User user = new User(username, email, passwordEncoder.encode(password));
        user = userRepository.save(user);
        
        return sessionManager.createSession(user);
    }

    public String getXAuthorizationUrl() {
        return xOAuthService.getAuthorizationUrl();
    }

    public XOAuthService.OAuthTokens handleXCallback(String code) {
        return xOAuthService.exchangeCodeForTokens(code);
    }

    public boolean validateSession(String token) {
        return sessionManager.validateSession(token);
    }

    public User getCurrentUser(String token) {
        String userId = sessionManager.getUserIdFromToken(token);
        return userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new SecurityException("User not found"));
    }
}
