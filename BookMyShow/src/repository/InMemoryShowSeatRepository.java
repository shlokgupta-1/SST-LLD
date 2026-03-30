package repository;

import models.ShowSeat;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Composite key: showId + "_" + seatId — uniquely identifies a show-seat pair.
 */
public class InMemoryShowSeatRepository implements ShowSeatRepository {

    // Key: "showId_seatId"
    private final Map<String, ShowSeat> store = new ConcurrentHashMap<>();

    @Override
    public void save(ShowSeat showSeat) {
        store.put(key(showSeat.getShowId(), showSeat.getSeat().getId()), showSeat);
    }

    @Override
    public void saveAll(List<ShowSeat> showSeats) {
        showSeats.forEach(this::save);
    }

    @Override
    public Optional<ShowSeat> findByShowIdAndSeatId(String showId, String seatId) {
        return Optional.ofNullable(store.get(key(showId, seatId)));
    }

    @Override
    public List<ShowSeat> findByShowId(String showId) {
        return store.values().stream()
                .filter(ss -> ss.getShowId().equals(showId))
                .collect(Collectors.toList());
    }

    private String key(String showId, String seatId) {
        return showId + "_" + seatId;
    }
}
