package service;

import enums.Genre;
import enums.Language;
import models.City;
import models.Movie;
import models.Show;
import models.Theatre;
import repository.MovieRepository;
import repository.ShowRepository;
import repository.TheatreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CatalogueService {

    private final MovieRepository   movieRepository;
    private final TheatreRepository theatreRepository;
    private final ShowRepository    showRepository;

    public CatalogueService(MovieRepository movieRepository,
                            TheatreRepository theatreRepository,
                            ShowRepository showRepository) {
        this.movieRepository   = movieRepository;
        this.theatreRepository = theatreRepository;
        this.showRepository    = showRepository;
    }

    public List<Movie> getMoviesByCity(City city) {
        List<String> theatreIds = theatreRepository.findByCityId(city.getId())
                .stream().map(t -> t.getId()).collect(Collectors.toList());

        return showRepository.findAll().stream()
                .filter(s -> theatreIds.contains(s.getTheatre().getId()) && s.isActive())
                .map(s -> s.getMovie())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Theatre> getTheatresByCity(City city) {
        return theatreRepository.findByCityId(city.getId());
    }

    public List<Show> getShowsByMovie(Movie movie, City city) {
        List<String> theatreIds = theatreRepository.findByCityId(city.getId())
                .stream().map(t -> t.getId()).collect(Collectors.toList());

        return showRepository.findByMovieId(movie.getId()).stream()
                .filter(s -> theatreIds.contains(s.getTheatre().getId()))
                .collect(Collectors.toList());
    }

    public List<Movie> searchByLanguage(Language language) {
        return movieRepository.findByLanguage(language);
    }

    public List<Movie> searchByGenre(Genre genre) {
        return movieRepository.findByGenre(genre);
    }

    public Optional<Movie> findMovieById(String movieId) {
        return movieRepository.findById(movieId);
    }

    public Optional<Show> findShowById(String showId) {
        return showRepository.findById(showId);
    }
}
