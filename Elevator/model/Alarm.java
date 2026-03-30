package elevator.model;

public class Alarm {

    private boolean triggered;

    public Alarm() {
        this.triggered = false;
    }

    public void trigger() {
        this.triggered = true;
        System.out.println("  🔔 ALARM TRIGGERED — Elevator overloaded!");
    }

    public void reset() {
        this.triggered = false;
        System.out.println("  ✅ Alarm reset.");
    }

    public boolean isTriggered() {
        return triggered;
    }
}
