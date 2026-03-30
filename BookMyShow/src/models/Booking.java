package models;

import enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Booking {
    private final String id;
    private final User user;
    private final Show show;
    private final List<ShowSeat> seats;
    private BookingStatus status;
    private final LocalDateTime createdAt;
    private double totalAmount;

    public Booking(User user, Show show, List<ShowSeat> seats) {
        this.id          = UUID.randomUUID().toString();
        this.user        = user;
        this.show        = show;
        this.seats       = seats;
        this.status      = BookingStatus.CREATED;
        this.createdAt   = LocalDateTime.now();
        this.totalAmount = seats.stream().mapToDouble(ShowSeat::getPrice).sum();
    }

    public String getId()              { return id; }
    public User getUser()              { return user; }
    public Show getShow()              { return show; }
    public List<ShowSeat> getSeats()   { return seats; }
    public BookingStatus getStatus()   { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public double getTotalAmount()     { return totalAmount; }

    public void setStatus(BookingStatus status) { this.status = status; }
    public void setTotalAmount(double amount)   { this.totalAmount = amount; }

    @Override
    public String toString() {
        return "Booking{id=" + id + ", user=" + user.getName()
                + ", show=" + show.getMovie().getTitle()
                + ", seats=" + seats.size()
                + ", amount=" + totalAmount
                + ", status=" + status + "}";
    }
}
