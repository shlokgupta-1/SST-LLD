package service;

import models.ShowSeat;
import repository.ShowSeatRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SeatService {

    private final ShowSeatRepository showSeatRepository;

    public SeatService(ShowSeatRepository showSeatRepository) {
        this.showSeatRepository = showSeatRepository;
    }

    public List<ShowSeat> getAvailableSeats(String showId) {
        return showSeatRepository.findByShowId(showId).stream()
                .filter(ShowSeat::isAvailable)
                .collect(Collectors.toList());
    }

    public List<ShowSeat> getAllSeats(String showId) {
        return showSeatRepository.findByShowId(showId);
    }
}
