package elevator.state;

import elevator.model.Elevator;

public class MaintenanceState implements ElevatorState {

    @Override
    public void move(Elevator elevator) {
        System.out.println("  [Elevator-" + elevator.getId() + "] 🔧 Under maintenance — cannot move.");
    }

    @Override
    public void openDoor(Elevator elevator) {
        System.out.println("  [Elevator-" + elevator.getId() + "] 🔧 Under maintenance — door disabled.");
    }

    @Override
    public void closeDoor(Elevator elevator) {
        System.out.println("  [Elevator-" + elevator.getId() + "] 🔧 Under maintenance — door disabled.");
    }

    @Override
    public String getStateName() {
        return "MAINTENANCE";
    }
}
