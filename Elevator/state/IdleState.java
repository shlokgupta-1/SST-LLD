package elevator.state;

import elevator.enums.Direction;
import elevator.enums.ElevatorStatus;
import elevator.model.Elevator;

public class IdleState implements ElevatorState {

    @Override
    public void move(Elevator elevator) {
        if (!elevator.getPendingStops().isEmpty()) {
            int next = elevator.getPendingStops().first();
            if (next > elevator.getCurrentFloor()) {
                elevator.setDirection(Direction.UP);
                elevator.setState(new MovingUpState());
            } else if (next < elevator.getCurrentFloor()) {
                elevator.setDirection(Direction.DOWN);
                elevator.setState(new MovingDownState());
            }
            elevator.getState().openDoor(elevator);
        }
    }

    @Override
    public void openDoor(Elevator elevator) {
        System.out.println("  [Elevator-" + elevator.getId() + "] Opening door (was idle).");
        elevator.getDoor().open();
        elevator.setState(new DoorOpenState());
        elevator.setStatus(ElevatorStatus.DOOR_OPEN);
    }

    @Override
    public void closeDoor(Elevator elevator) {
        System.out.println("  [Elevator-" + elevator.getId() + "] Door already closed (idle).");
    }

    @Override
    public String getStateName() {
        return "IDLE";
    }
}
