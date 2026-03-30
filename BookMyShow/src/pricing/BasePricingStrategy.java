package pricing;

import enums.SeatType;
import models.Show;
import models.ShowSeat;

import java.util.List;
import java.util.Map;

public class BasePricingStrategy implements PricingStrategy {

    private static final Map<SeatType, Double> BASE_PRICES = Map.of(
            SeatType.SILVER,   150.0,
            SeatType.GOLD,     250.0,
            SeatType.PLATINUM, 400.0,
            SeatType.RECLINER, 600.0
    );

    @Override
    public void applyPricing(Show show, List<ShowSeat> showSeats) {
        for (ShowSeat showSeat : showSeats) {
            SeatType type  = showSeat.getSeat().getSeatType();
            double price   = BASE_PRICES.getOrDefault(type, 200.0);
            showSeat.setPrice(price);
        }
        System.out.println("[BasePricing] Applied base prices to show: " + show.getMovie().getTitle());
    }
}
