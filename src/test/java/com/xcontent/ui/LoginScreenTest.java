package com.xcontent.ui;

import com.xcontent.application.XContentApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.assertions.api.Assertions;
import javafx.stage.Stage;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        // ダイアログが表示されるのを待つ
        WaitForAsyncUtils.waitForFxEvents();
        
        // DialogPaneを検索
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane, "Error dialog should be shown");
        assertEquals("Invalid username or password", dialogPane.getContentText());
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
        WaitForAsyncUtils.waitForFxEvents();
        Pane dashboardPane = robot.lookup("#dashboardPane").queryAs(Pane.class);
        Assertions.assertThat(dashboardPane)
            .as("Dashboard pane")
            .isNotNull()
            .isVisible();
    }
}
