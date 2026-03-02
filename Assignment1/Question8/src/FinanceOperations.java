public interface FinanceOperations {
    void addToLedger(int amount, String source);
    int getLedgerBalance();
}