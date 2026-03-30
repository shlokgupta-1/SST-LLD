package repository;

import models.Show;

import java.util.List;
import java.util.Optional;

public interface ShowRepository {
    void save(Show show);
    Optional<Show> findById(String id);
    List<Show> findAll();
    List<Show> findByMovieId(String movieId);
    List<Show> findByTheatreId(String theatreId);
    void delete(String showId);
}
