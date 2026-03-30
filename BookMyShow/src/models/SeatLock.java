package models;

import java.time.LocalDateTime;

public class SeatLock {
    private final String showSeatKey;
    private final String bookingId;
    private final String userId;
    private final LocalDateTime lockedAt;
    private final LocalDateTime expiresAt;

    public SeatLock(String showSeatKey, String bookingId, String userId, long ttlSeconds) {
        this.showSeatKey = showSeatKey;
        this.bookingId   = bookingId;
        this.userId      = userId;
        this.lockedAt    = LocalDateTime.now();
        this.expiresAt   = lockedAt.plusSeconds(ttlSeconds);
    }

    public String getShowSeatKey() { return showSeatKey; }
    public String getBookingId()   { return bookingId; }
    public String getUserId()      { return userId; }
    public LocalDateTime getLockedAt()  { return lockedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    @Override
    public String toString() {
        return "SeatLock{key=" + showSeatKey + ", bookingId=" + bookingId
                + ", expiresAt=" + expiresAt + "}";
    }
}
