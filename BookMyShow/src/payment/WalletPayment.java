package payment;

public class WalletPayment implements PaymentMethod {

    private final String walletId;
    private double balance;

    public WalletPayment(String walletId, double balance) {
        this.walletId = walletId;
        this.balance  = balance;
    }

    @Override
    public boolean pay(double amount) {
        if (balance < amount) {
            System.out.println("[Wallet] Insufficient balance. Available: ₹" + balance);
            return false;
        }
        balance -= amount;
        System.out.println("[Wallet] Paid ₹" + amount + " from wallet " + walletId
                + ". Remaining balance: ₹" + balance);
        return true;
    }

    @Override
    public boolean refund(double amount) {
        balance += amount;
        System.out.println("[Wallet] ₹" + amount + " refunded to wallet " + walletId
                + ". New balance: ₹" + balance);
        return true;
    }

    @Override
    public String getMethodName() { return "Wallet"; }
}
