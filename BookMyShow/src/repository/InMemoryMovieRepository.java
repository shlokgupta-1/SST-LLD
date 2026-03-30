package repository;

import enums.Genre;
import enums.Language;
import models.Movie;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of MovieRepository using ConcurrentHashMap.
 * ConcurrentHashMap is used here because multiple threads (admin adding movie,
 * users reading movies) can access this concurrently without explicit locking.
 */
public class InMemoryMovieRepository implements MovieRepository {

    private final Map<String, Movie> store = new ConcurrentHashMap<>();

    @Override
    public void save(Movie movie) {
        store.put(movie.getId(), movie);
    }

    @Override
    public Optional<Movie> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Movie> findByLanguage(Language language) {
        return store.values().stream()
                .filter(m -> m.getLanguage() == language)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> findByGenre(Genre genre) {
        return store.values().stream()
                .filter(m -> m.getGenres().contains(genre))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        store.remove(id);
    }
}
