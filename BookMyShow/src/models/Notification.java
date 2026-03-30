package models;

import enums.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private final String id;
    private final String userId;
    private final String message;
    private final NotificationType type;
    private final LocalDateTime sentAt;

    public Notification(String userId, String message, NotificationType type) {
        this.id     = UUID.randomUUID().toString();
        this.userId = userId;
        this.message = message;
        this.type   = type;
        this.sentAt = LocalDateTime.now();
    }

    public String getId()              { return id; }
    public String getUserId()          { return userId; }
    public String getMessage()         { return message; }
    public NotificationType getType()  { return type; }
    public LocalDateTime getSentAt()   { return sentAt; }

    @Override
    public String toString() {
        return "Notification{type=" + type + ", message=" + message + ", at=" + sentAt + "}";
    }
}
