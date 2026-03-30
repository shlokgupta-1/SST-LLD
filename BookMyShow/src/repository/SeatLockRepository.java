package repository;

import models.SeatLock;

import java.util.List;
import java.util.Optional;

public interface SeatLockRepository {
    void save(SeatLock seatLock);
    Optional<SeatLock> findByShowSeatKey(String showSeatKey);
    List<SeatLock> findAll();
    void remove(String showSeatKey);
}
