public class SimpleInvoiceFormatter implements InvoiceFormatter {

    @Override
    public String format(Invoice invoice) {

        StringBuilder sb = new StringBuilder();

        sb.append("Invoice ID: ").append(invoice.getInvoiceId()).append("\n");

        for (String line : invoice.getLineDescriptions()) {
            sb.append(line).append("\n");
        }

        sb.append("Subtotal: ").append(invoice.getSubtotal()).append("\n");
        sb.append("Tax: ").append(invoice.getTax()).append("\n");
        sb.append("Discount: ").append(invoice.getDiscount()).append("\n");
        sb.append("Total: ").append(invoice.getTotal()).append("\n");

        return sb.toString();
    }
}