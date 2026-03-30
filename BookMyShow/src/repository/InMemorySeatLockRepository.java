package repository;

import models.SeatLock;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySeatLockRepository implements SeatLockRepository {

    // Key: "showId_seatId"
    private final Map<String, SeatLock> store = new ConcurrentHashMap<>();

    @Override
    public void save(SeatLock seatLock) {
        store.put(seatLock.getShowSeatKey(), seatLock);
    }

    @Override
    public Optional<SeatLock> findByShowSeatKey(String showSeatKey) {
        return Optional.ofNullable(store.get(showSeatKey));
    }

    @Override
    public List<SeatLock> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void remove(String showSeatKey) {
        store.remove(showSeatKey);
    }
}
