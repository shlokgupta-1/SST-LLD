package service;

import enums.BookingStatus;
import enums.NotificationType;
import enums.SeatStatus;
import models.*;
import notification.NotificationService;
import repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingService {

    private static final long LOCK_TTL_SECONDS = 600;

    private final BookingRepository    bookingRepository;
    private final ShowSeatRepository   showSeatRepository;
    private final SeatLockRepository   seatLockRepository;
    private final ShowRepository       showRepository;
    private final NotificationService  notificationService;

    public BookingService(BookingRepository bookingRepository,
                          ShowSeatRepository showSeatRepository,
                          SeatLockRepository seatLockRepository,
                          ShowRepository showRepository,
                          NotificationService notificationService) {
        this.bookingRepository   = bookingRepository;
        this.showSeatRepository  = showSeatRepository;
        this.seatLockRepository  = seatLockRepository;
        this.showRepository      = showRepository;
        this.notificationService = notificationService;
    }

    public Booking createBooking(User user, String showId, List<String> seatIds) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + showId));

        List<ShowSeat> targetSeats = new ArrayList<>();
        for (String seatId : seatIds) {
            ShowSeat ss = showSeatRepository.findByShowIdAndSeatId(showId, seatId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Seat " + seatId + " not found in show " + showId));
            targetSeats.add(ss);
        }

        targetSeats.sort((a, b) ->
                (showId + "_" + a.getSeat().getId())
                        .compareTo(showId + "_" + b.getSeat().getId()));

        List<ShowSeat> locked = new ArrayList<>();
        try {
            for (ShowSeat ss : targetSeats) {
                ss.getLock().lock();
                locked.add(ss);
            }

            for (ShowSeat ss : targetSeats) {
                if (!ss.isAvailable()) {
                    throw new IllegalStateException(
                            "Seat " + ss.getSeat().getSeatNumber() + " is no longer available.");
                }
            }

            for (ShowSeat ss : targetSeats) {
                ss.setStatus(SeatStatus.LOCKED);
                showSeatRepository.save(ss);
            }

            Booking booking = new Booking(user, show, targetSeats);
            booking.setStatus(BookingStatus.LOCKED);
            bookingRepository.save(booking);

            for (ShowSeat ss : targetSeats) {
                String key = showId + "_" + ss.getSeat().getId();
                SeatLock seatLock = new SeatLock(key, booking.getId(), user.getId(), LOCK_TTL_SECONDS);
                seatLockRepository.save(seatLock);
            }

            System.out.println("[BookingService] Booking created: " + booking.getId()
                    + " for user " + user.getName()
                    + " | seats=" + seatIds.size()
                    + " | total=₹" + booking.getTotalAmount());

            return booking;

        } finally {
            for (ShowSeat ss : locked) {
                ss.getLock().unlock();
            }
        }
    }

    public void confirmBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));

        if (booking.getStatus() != BookingStatus.LOCKED) {
            throw new IllegalStateException("Cannot confirm booking in state: " + booking.getStatus());
        }

        for (ShowSeat ss : booking.getSeats()) {
            ss.setStatus(SeatStatus.BOOKED);
            showSeatRepository.save(ss);
            String key = booking.getShow().getId() + "_" + ss.getSeat().getId();
            seatLockRepository.remove(key);
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        notificationService.notify(
                booking.getUser().getId(),
                "Your booking " + bookingId + " is confirmed! Enjoy the show 🎬",
                NotificationType.BOOKING_CONFIRMED
        );

        System.out.println("[BookingService] Booking CONFIRMED: " + bookingId);
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking already cancelled.");
        }

        for (ShowSeat ss : booking.getSeats()) {
            ss.setStatus(SeatStatus.AVAILABLE);
            showSeatRepository.save(ss);
            String key = booking.getShow().getId() + "_" + ss.getSeat().getId();
            seatLockRepository.remove(key);
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        notificationService.notify(
                booking.getUser().getId(),
                "Your booking " + bookingId + " has been cancelled. Refund will be processed shortly.",
                NotificationType.BOOKING_CANCELLED
        );

        System.out.println("[BookingService] Booking CANCELLED: " + bookingId);
    }

    public Optional<Booking> findById(String bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId);
    }
}
