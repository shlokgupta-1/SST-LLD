package pricing;

import enums.SeatStatus;
import enums.SeatType;
import models.Show;
import models.ShowSeat;

import java.util.List;
import java.util.Map;

public class DynamicPricingStrategy implements PricingStrategy {

    private static final Map<SeatType, Double> BASE_PRICES = Map.of(
            SeatType.SILVER,   150.0,
            SeatType.GOLD,     250.0,
            SeatType.PLATINUM, 400.0,
            SeatType.RECLINER, 600.0
    );

    private final double occupancyThreshold;
    private final double surgeMultiplier;

    public DynamicPricingStrategy(double occupancyThreshold, double surgeMultiplier) {
        this.occupancyThreshold = occupancyThreshold;
        this.surgeMultiplier    = surgeMultiplier;
    }

    @Override
    public void applyPricing(Show show, List<ShowSeat> showSeats) {
        long bookedCount = showSeats.stream()
                .filter(ss -> ss.getStatus() == SeatStatus.BOOKED
                           || ss.getStatus() == SeatStatus.LOCKED)
                .count();

        double occupancy = (double) bookedCount / showSeats.size();
        boolean isSurge  = occupancy >= occupancyThreshold;

        for (ShowSeat showSeat : showSeats) {
            if (showSeat.getStatus() == SeatStatus.AVAILABLE) {
                double base  = BASE_PRICES.getOrDefault(showSeat.getSeat().getSeatType(), 200.0);
                double price = isSurge ? base * surgeMultiplier : base;
                showSeat.setPrice(price);
            }
        }

        System.out.printf("[DynamicPricing] Show '%s' occupancy=%.0f%%. %s applied.%n",
                show.getMovie().getTitle(), occupancy * 100,
                isSurge ? "SURGE (×" + surgeMultiplier + ")" : "Normal pricing");
    }
}
