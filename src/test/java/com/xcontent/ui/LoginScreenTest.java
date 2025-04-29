package com.xcontent.ui;

import com.xcontent.application.XContentApplication;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.stage.Stage;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class LoginScreenTest {

    @Start
    private void start(Stage stage) {
        new XContentApplication().start(stage);
    }

    @Test
    void shouldShowLoginError_WhenCredentialsAreInvalid(FxRobot robot) {
        // Arrange
        robot.clickOn("#loginMenuItem");

        // Act
        robot.clickOn("#usernameField").write("invaliduser");
        robot.clickOn("#passwordField").write("invalidpass");
        robot.clickOn("#loginButton");

        // Assert
        assertThat(robot.lookup(".alert").queryAs(javafx.scene.control.Alert.class))
            .hasContentText("Invalid username or password");
    }

    @Test
    void shouldNavigateToDashboard_WhenLoginIsSuccessful(FxRobot robot) {
        // Arrange
        robot.clickOn("#loginMenuItem");

        // Act
        robot.clickOn("#usernameField").write("testuser");
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#loginButton");

        // Assert
        assertThat(robot.lookup("#dashboardPane").query()).isVisible();
    }
}
