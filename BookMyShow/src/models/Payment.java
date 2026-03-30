package models;

import enums.PaymentStatus;
import payment.PaymentMethod;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private final String id;
    private final String bookingId;
    private final double amount;
    private final PaymentMethod method;
    private PaymentStatus status;
    private final LocalDateTime initiatedAt;
    private LocalDateTime completedAt;

    public Payment(String bookingId, double amount, PaymentMethod method) {
        this.id          = UUID.randomUUID().toString();
        this.bookingId   = bookingId;
        this.amount      = amount;
        this.method      = method;
        this.status      = PaymentStatus.INITIATED;
        this.initiatedAt = LocalDateTime.now();
    }

    public String getId()              { return id; }
    public String getBookingId()       { return bookingId; }
    public double getAmount()          { return amount; }
    public PaymentMethod getMethod()   { return method; }
    public PaymentStatus getStatus()   { return status; }
    public LocalDateTime getInitiatedAt() { return initiatedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }

    public void setStatus(PaymentStatus status) {
        this.status      = status;
        this.completedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Payment{id=" + id + ", bookingId=" + bookingId
                + ", amount=" + amount + ", method=" + method.getClass().getSimpleName()
                + ", status=" + status + "}";
    }
}
