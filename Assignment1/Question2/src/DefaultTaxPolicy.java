public class DefaultTaxPolicy implements TaxPolicy {

    @Override
    public double taxPercent(String customerType) {
        switch (customerType.toLowerCase()) {
            case "student":
                return 5.0;
            case "staff":
                return 10.0;
            default:
                return 12.0;
        }
    }
}