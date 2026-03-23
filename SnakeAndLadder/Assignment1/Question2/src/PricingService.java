import java.util.List;
import java.util.Map;

public class PricingService {

    public double calculateSubtotal(List<OrderLine> lines,
                                    Map<String, MenuItem> menu,
                                    List<String> descriptions) {

        double subtotal = 0;

        for (OrderLine line : lines) {
            MenuItem item = menu.get(line.getItemId());
            double lineTotal = item.getPrice() * line.getQuantity();
            subtotal += lineTotal;

            descriptions.add(item.getName() + " x" + line.getQuantity() + " = " + lineTotal);
        }

        return subtotal;
    }
}