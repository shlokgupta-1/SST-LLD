public class ClubConsole {

    public void run(FinanceOperations finance,
                    MinutesOperations minutes,
                    EventOperations events) {

        System.out.println("=== Club Admin ===");

        finance.addToLedger(5000, "sponsor");
        minutes.addMinutes("Meeting at 5pm");
        events.createEvent("HackNight", 2000);

        System.out.println("Summary: ledgerBalance="
                + finance.getLedgerBalance()
                + ", minutes=" + minutes.getMinutesCount()
                + ", events=" + events.getEventCount());
    }
}