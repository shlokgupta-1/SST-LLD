package repository;

import models.ShowSeat;

import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository {
    void save(ShowSeat showSeat);
    void saveAll(List<ShowSeat> showSeats);
    Optional<ShowSeat> findByShowIdAndSeatId(String showId, String seatId);
    List<ShowSeat> findByShowId(String showId);
}
