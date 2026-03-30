package notification;

import models.Notification;

public class SmsNotificationObserver implements NotificationObserver {

    @Override
    public void onNotification(Notification notification) {
        System.out.println("[SMS] → userId=" + notification.getUserId()
                + " | Type: " + notification.getType()
                + " | " + notification.getMessage());
    }
}
