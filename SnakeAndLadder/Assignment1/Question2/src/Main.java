import java.util.*;

public class Main{

    public static void main(String[] args) {

        TaxPolicy taxPolicy = new DefaultTaxPolicy();
        DiscountPolicy discountPolicy = new DefaultDiscountPolicy();
        InvoiceFormatter formatter = new SimpleInvoiceFormatter();
        InvoiceRepository repository = new FileStore();
        PricingService pricingService = new PricingService();

        CafeteriaSystem system = new CafeteriaSystem(
                taxPolicy,
                discountPolicy,
                formatter,
                repository,
                pricingService
        );

        List<OrderLine> order = Arrays.asList(
                new OrderLine("C1", 2),
                new OrderLine("S1", 1),
                new OrderLine("B1", 1)
        );

        String invoice = system.checkout("student", order);

        System.out.println(invoice);
    }
}