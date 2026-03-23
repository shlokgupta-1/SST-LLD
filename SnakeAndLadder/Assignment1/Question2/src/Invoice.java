import java.util.List;

public class Invoice {

    private final String invoiceId;
    private final List<String> lineDescriptions;
    private final double subtotal;
    private final double tax;
    private final double discount;
    private final double total;

    public Invoice(String invoiceId,
                   List<String> lineDescriptions,
                   double subtotal,
                   double tax,
                   double discount,
                   double total) {
        this.invoiceId = invoiceId;
        this.lineDescriptions = lineDescriptions;
        this.subtotal = subtotal;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public List<String> getLineDescriptions() {
        return lineDescriptions;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTax() {
        return tax;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }
}