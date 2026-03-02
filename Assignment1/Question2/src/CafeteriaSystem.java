import java.util.*;

public class CafeteriaSystem {

    private final Map<String, MenuItem> menu = new HashMap<>();
    private final TaxPolicy taxPolicy;
    private final DiscountPolicy discountPolicy;
    private final InvoiceFormatter formatter;
    private final InvoiceRepository repository;
    private final PricingService pricingService;

    public CafeteriaSystem(TaxPolicy taxPolicy,
                           DiscountPolicy discountPolicy,
                           InvoiceFormatter formatter,
                           InvoiceRepository repository,
                           PricingService pricingService) {

        this.taxPolicy = taxPolicy;
        this.discountPolicy = discountPolicy;
        this.formatter = formatter;
        this.repository = repository;
        this.pricingService = pricingService;

        seedMenu();
    }

    private void seedMenu() {
        menu.put("C1", new MenuItem("C1", "Coffee", 120));
        menu.put("T1", new MenuItem("T1", "Tea", 80));
        menu.put("S1", new MenuItem("S1", "Sandwich", 250));
        menu.put("B1", new MenuItem("B1", "Burger", 300));
    }

    public String checkout(String customerType, List<OrderLine> lines) {

        String invoiceId = "INV-" + System.currentTimeMillis();

        List<String> descriptions = new ArrayList<>();

        double subtotal = pricingService.calculateSubtotal(lines, menu, descriptions);

        double taxPercent = taxPolicy.taxPercent(customerType);
        double tax = subtotal * taxPercent / 100;

        double discount = discountPolicy.discountAmount(customerType, subtotal, lines.size());

        double total = subtotal + tax - discount;

        Invoice invoice = new Invoice(
                invoiceId,
                descriptions,
                subtotal,
                tax,
                discount,
                total
        );

        String formatted = formatter.format(invoice);

        repository.save(invoiceId, formatted);

        return formatted;
    }
}