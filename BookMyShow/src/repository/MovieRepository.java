package repository;

import models.Movie;
import enums.Genre;
import enums.Language;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Movie.
 * Follows the Repository pattern: abstracts data access from business logic.
 * You can swap this for a JPA implementation without touching services.
 */
public interface MovieRepository {
    void save(Movie movie);
    Optional<Movie> findById(String id);
    List<Movie> findAll();
    List<Movie> findByLanguage(Language language);
    List<Movie> findByGenre(Genre genre);
    void delete(String id);
}
