package service;

import models.*;
import pricing.PricingStrategy;
import repository.*;

import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private final MovieRepository    movieRepository;
    private final TheatreRepository  theatreRepository;
    private final ShowRepository     showRepository;
    private final ShowSeatRepository showSeatRepository;

    public AdminService(MovieRepository movieRepository,
                        TheatreRepository theatreRepository,
                        ShowRepository showRepository,
                        ShowSeatRepository showSeatRepository) {
        this.movieRepository    = movieRepository;
        this.theatreRepository  = theatreRepository;
        this.showRepository     = showRepository;
        this.showSeatRepository = showSeatRepository;
    }

    public Movie addMovie(Movie movie) {
        movieRepository.save(movie);
        System.out.println("[AdminService] Movie added: " + movie.getTitle());
        return movie;
    }

    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
        System.out.println("[AdminService] Movie updated: " + movie.getTitle());
    }

    public void removeMovie(String movieId) {
        movieRepository.delete(movieId);
        System.out.println("[AdminService] Movie removed: " + movieId);
    }

    public Theatre addTheatre(Theatre theatre) {
        theatreRepository.save(theatre);
        System.out.println("[AdminService] Theatre added: " + theatre.getName());
        return theatre;
    }

    public Show addShow(Show show, double basePrice) {
        showRepository.save(show);

        List<ShowSeat> showSeats = new ArrayList<>();
        for (Seat seat : show.getScreen().getSeats()) {
            showSeats.add(new ShowSeat(show.getId(), seat, basePrice));
        }
        showSeatRepository.saveAll(showSeats);

        System.out.println("[AdminService] Show added: " + show.getMovie().getTitle()
                + " @ " + show.getTheatre().getName()
                + " (" + showSeats.size() + " seats created)");
        return show;
    }

    public void updateShow(Show show) {
        showRepository.save(show);
        System.out.println("[AdminService] Show updated: " + show.getId());
    }

    public void deleteShow(String showId) {
        showRepository.findById(showId).ifPresent(s -> {
            s.setActive(false);
            showRepository.save(s);
        });
        System.out.println("[AdminService] Show deactivated: " + showId);
    }

    public void applyDynamicPricing(String showId, PricingStrategy strategy) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + showId));
        List<ShowSeat> showSeats = showSeatRepository.findByShowId(showId);
        strategy.applyPricing(show, showSeats);
        showSeats.forEach(showSeatRepository::save);
    }
}
