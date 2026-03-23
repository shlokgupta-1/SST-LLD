public class TreasurerTool implements FinanceOperations {

    private final BudgetLedger ledger;

    public TreasurerTool(BudgetLedger ledger) {
        this.ledger = ledger;
    }

    @Override
    public void addToLedger(int amount, String source) {
        ledger.add(amount, source);
    }

    @Override
    public int getLedgerBalance() {
        return ledger.getBalance();
    }
}