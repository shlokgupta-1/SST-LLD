package payment;

public class DebitCardPayment implements PaymentMethod {

    private final String cardNumber;

    public DebitCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("[DebitCard] Deducting ₹" + amount + " using debit card ending "
                + cardNumber.substring(cardNumber.length() - 4));
        return true;
    }

    @Override
    public boolean refund(double amount) {
        System.out.println("[DebitCard] Refunding ₹" + amount + " to debit card ending "
                + cardNumber.substring(cardNumber.length() - 4));
        return true;
    }

    @Override
    public String getMethodName() { return "DebitCard"; }
}
