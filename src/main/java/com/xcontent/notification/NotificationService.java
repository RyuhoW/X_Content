package com.xcontent.notification;

import com.xcontent.model.Notification;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    private final List<Notification> notifications;
    private final List<NotificationListener> listeners;

    public NotificationService() {
        this.notifications = new CopyOnWriteArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public void sendNotification(String title, String message, String type) {
        Notification notification = new Notification(title, message, type);
        notifications.add(notification);
        
        // リスナーに通知
        notifyListeners(notification);
        
        // UIスレッドでアラート表示
        if ("error".equals(type)) {
            Platform.runLater(() -> showAlert(notification));
        }
        
        logger.info("Notification sent: {}", title);
    }

    public void addListener(NotificationListener listener) {
        listeners.add(listener);
    }

    public void removeListener(NotificationListener listener) {
        listeners.remove(listener);
    }

    public List<Notification> getUnreadNotifications() {
        return notifications.stream()
                .filter(n -> !n.isRead())
                .toList();
    }

    public void markAsRead(Notification notification) {
        notification.setRead(true);
    }

    private void notifyListeners(Notification notification) {
        for (NotificationListener listener : listeners) {
            listener.onNotification(notification);
        }
    }

    private void showAlert(Notification notification) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(notification.getTitle());
        alert.setHeaderText(null);
        alert.setContentText(notification.getMessage());
        alert.show();
    }

    public interface NotificationListener {
        void onNotification(Notification notification);
    }
}
