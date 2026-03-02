public class EventLeadTool implements EventOperations {

    private final EventPlanner planner;

    public EventLeadTool(EventPlanner planner) {
        this.planner = planner;
    }

    @Override
    public void createEvent(String name, int budget) {
        planner.create(name, budget);
    }

    @Override
    public int getEventCount() {
        return planner.count();
    }
}