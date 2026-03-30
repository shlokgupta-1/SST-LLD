package notification;

import models.Notification;

public class EmailNotificationObserver implements NotificationObserver {

    @Override
    public void onNotification(Notification notification) {
        System.out.println("[EMAIL] → userId=" + notification.getUserId()
                + " | Type: " + notification.getType()
                + " | Message: " + notification.getMessage());
    }
}
