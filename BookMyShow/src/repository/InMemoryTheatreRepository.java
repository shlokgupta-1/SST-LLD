package repository;

import models.Theatre;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryTheatreRepository implements TheatreRepository {

    private final Map<String, Theatre> store = new ConcurrentHashMap<>();

    @Override
    public void save(Theatre theatre) {
        store.put(theatre.getId(), theatre);
    }

    @Override
    public Optional<Theatre> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Theatre> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Theatre> findByCityId(String cityId) {
        return store.values().stream()
                .filter(t -> t.getCity().getId().equals(cityId))
                .collect(Collectors.toList());
    }
}
