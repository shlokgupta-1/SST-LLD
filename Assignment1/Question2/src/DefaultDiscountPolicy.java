public class DefaultDiscountPolicy implements DiscountPolicy {

    @Override
    public double discountAmount(String customerType, double subtotal, int distinctItems) {

        double discount = 0;

        if (customerType.equalsIgnoreCase("student") && subtotal > 500) {
            discount += subtotal * 0.10;
        }

        if (distinctItems >= 3) {
            discount += 50;
        }

        return discount;
    }
}