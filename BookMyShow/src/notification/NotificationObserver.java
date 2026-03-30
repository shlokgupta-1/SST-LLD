package notification;

import models.Notification;

public interface NotificationObserver {
    void onNotification(Notification notification);
}
