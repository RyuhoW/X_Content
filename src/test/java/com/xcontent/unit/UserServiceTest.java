package com.xcontent.unit;

import com.xcontent.model.User;
import com.xcontent.repository.UserRepository;
import com.xcontent.security.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private AuthenticationService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthenticationService(userRepository);
    }

    @Test
    void registerUser_WithValidData_ShouldSucceed() {
        // Arrange
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User user = i.getArgument(0);
            user.setId(1L);
            return user;
        });

        // Act
        String token = authService.registerUser(username, email, password);

        // Assert
        assertNotNull(token);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_WithExistingUsername_ShouldThrowException() {
        // Arrange
        String username = "existinguser";
        String email = "test@example.com";
        String password = "password123";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        // Act & Assert
        assertThrows(SecurityException.class, () -> 
            authService.registerUser(username, email, password)
        );
    }

    @Test
    void authenticateUser_WithValidCredentials_ShouldSucceed() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        User user = new User(username, "test@example.com", "hashedPassword");
        user.setId(1L);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        String token = authService.authenticateUser(username, password);

        // Assert
        assertNotNull(token);
    }
}
