public class Main {

    public static void main(String[] args) {

        BudgetLedger ledger = new BudgetLedger();
        MinutesBook minutesBook = new MinutesBook();
        EventPlanner planner = new EventPlanner();

        TreasurerTool treasurer = new TreasurerTool(ledger);
        SecretaryTool secretary = new SecretaryTool(minutesBook);
        EventLeadTool eventLead = new EventLeadTool(planner);

        ClubConsole console = new ClubConsole();
        console.run(treasurer, secretary, eventLead);
    }
}