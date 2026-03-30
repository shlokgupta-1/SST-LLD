package models;

import enums.SeatStatus;

import java.util.concurrent.locks.ReentrantLock;

public class ShowSeat {
    private final String showId;
    private final Seat seat;
    private SeatStatus status;
    private double price;

    private final ReentrantLock lock = new ReentrantLock();

    public ShowSeat(String showId, Seat seat, double price) {
        this.showId = showId;
        this.seat   = seat;
        this.price  = price;
        this.status = SeatStatus.AVAILABLE;
    }

    public String getShowId()      { return showId; }
    public Seat getSeat()          { return seat; }
    public double getPrice()       { return price; }
    public SeatStatus getStatus()  { return status; }
    public ReentrantLock getLock() { return lock; }

    public void setStatus(SeatStatus status) { this.status = status; }
    public void setPrice(double price)       { this.price = price; }

    public boolean isAvailable() {
        return status == SeatStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return "ShowSeat{seat=" + seat.getSeatNumber()
                + ", status=" + status + ", price=" + price + "}";
    }
}
