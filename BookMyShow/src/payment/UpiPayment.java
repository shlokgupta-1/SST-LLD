package payment;

public class UpiPayment implements PaymentMethod {

    private final String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("[UPI] Collecting ₹" + amount + " from UPI ID: " + upiId);
        return true;
    }

    @Override
    public boolean refund(double amount) {
        System.out.println("[UPI] Refunding ₹" + amount + " to UPI ID: " + upiId);
        return true;
    }

    @Override
    public String getMethodName() { return "UPI"; }
}
