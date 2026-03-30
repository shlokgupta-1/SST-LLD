package pricing;

import models.Show;
import models.ShowSeat;

import java.util.List;

public interface PricingStrategy {
    void applyPricing(Show show, List<ShowSeat> showSeats);
}
