package repository;

import models.Booking;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBookingRepository implements BookingRepository {

    private final Map<String, Booking> store = new ConcurrentHashMap<>();

    @Override
    public void save(Booking booking) {
        store.put(booking.getId(), booking);
    }

    @Override
    public Optional<Booking> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Booking> findByUserId(String userId) {
        return store.values().stream()
                .filter(b -> b.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(store.values());
    }
}
