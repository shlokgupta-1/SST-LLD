package repository;

import models.Show;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryShowRepository implements ShowRepository {

    private final Map<String, Show> store = new ConcurrentHashMap<>();

    @Override
    public void save(Show show) {
        store.put(show.getId(), show);
    }

    @Override
    public Optional<Show> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Show> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Show> findByMovieId(String movieId) {
        return store.values().stream()
                .filter(s -> s.getMovie().getId().equals(movieId) && s.isActive())
                .collect(Collectors.toList());
    }

    @Override
    public List<Show> findByTheatreId(String theatreId) {
        return store.values().stream()
                .filter(s -> s.getTheatre().getId().equals(theatreId) && s.isActive())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String showId) {
        store.remove(showId);
    }
}
