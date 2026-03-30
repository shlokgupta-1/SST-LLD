package payment;

public class CreditCardPayment implements PaymentMethod {

    private final String cardNumber;
    private final String cardHolder;

    public CreditCardPayment(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public boolean pay(double amount) {
        // Simulate card charge (in reality: call payment gateway API)
        System.out.println("[CreditCard] Charging ₹" + amount + " to card ending "
                + cardNumber.substring(cardNumber.length() - 4)
                + " (" + cardHolder + ")");
        return true; // always succeeds in our simulation
    }

    @Override
    public boolean refund(double amount) {
        System.out.println("[CreditCard] Refunding ₹" + amount + " to card ending "
                + cardNumber.substring(cardNumber.length() - 4));
        return true;
    }

    @Override
    public String getMethodName() { return "CreditCard"; }
}
