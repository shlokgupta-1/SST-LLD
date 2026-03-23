import java.util.List;

public class AddOnPricingService {

    public Money calculate(List<AddOn> addOns) {

        int total = 0;

        for (AddOn addOn : addOns) {
            total += addOn.getPrice();
        }

        return new Money(total);
    }
}