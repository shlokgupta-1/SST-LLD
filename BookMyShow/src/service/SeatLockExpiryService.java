package service;

import enums.NotificationType;
import enums.SeatStatus;
import models.SeatLock;
import notification.NotificationService;
import repository.SeatLockRepository;
import repository.ShowSeatRepository;

import java.util.List;

public class SeatLockExpiryService {

    private final SeatLockRepository  seatLockRepository;
    private final ShowSeatRepository  showSeatRepository;
    private final NotificationService notificationService;

    public SeatLockExpiryService(SeatLockRepository seatLockRepository,
                                 ShowSeatRepository showSeatRepository,
                                 NotificationService notificationService) {
        this.seatLockRepository  = seatLockRepository;
        this.showSeatRepository  = showSeatRepository;
        this.notificationService = notificationService;
    }

    public void expireLockedSeats() {
        List<SeatLock> allLocks = seatLockRepository.findAll();
        for (SeatLock lock : allLocks) {
            if (lock.isExpired()) {
                String[] parts = lock.getShowSeatKey().split("_", 2);
                String showId  = parts[0];
                String seatId  = parts[1];

                showSeatRepository.findByShowIdAndSeatId(showId, seatId).ifPresent(ss -> {
                    if (ss.getStatus() == SeatStatus.LOCKED) {
                        ss.setStatus(SeatStatus.AVAILABLE);
                        showSeatRepository.save(ss);
                        System.out.println("[LockExpiry] Released expired seat: "
                                + ss.getSeat().getSeatNumber());
                    }
                });

                seatLockRepository.remove(lock.getShowSeatKey());

                notificationService.notify(
                        lock.getUserId(),
                        "Your seat hold has expired. Please rebook.",
                        NotificationType.SEAT_LOCK_EXPIRED
                );
            }
        }
    }
}
