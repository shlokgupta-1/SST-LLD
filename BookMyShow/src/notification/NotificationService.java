package notification;

import enums.NotificationType;
import models.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private final List<NotificationObserver> observers = new ArrayList<>();

    public void registerObserver(NotificationObserver observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(NotificationObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notify(String userId, String message, NotificationType type) {
        Notification notification = new Notification(userId, message, type);
        List<NotificationObserver> snapshot;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }
        for (NotificationObserver observer : snapshot) {
            observer.onNotification(notification);
        }
    }
}
