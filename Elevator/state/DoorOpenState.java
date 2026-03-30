package elevator.state;

import elevator.enums.Direction;
import elevator.enums.ElevatorStatus;
import elevator.model.Elevator;

public class DoorOpenState implements ElevatorState {

    @Override
    public void move(Elevator elevator) {
        System.out.println("  [Elevator-" + elevator.getId() + "] Cannot move — door is open!");
    }

    @Override
    public void openDoor(Elevator elevator) {
        System.out.println("  [Elevator-" + elevator.getId() + "] Door is already open.");
    }

    @Override
    public void closeDoor(Elevator elevator) {
        if (elevator.getWeightSensor().isOverloaded()) {
            System.out.println("  [Elevator-" + elevator.getId() + "] ⚠️  Overloaded! Cannot close door.");
            elevator.getAlarm().trigger();
            return;
        }
        System.out.println("  [Elevator-" + elevator.getId() + "] Closing door.");
        elevator.getDoor().close();

        if (!elevator.getPendingStops().isEmpty()) {
            int next = elevator.getPendingStops().first();
            if (next >= elevator.getCurrentFloor()) {
                elevator.setDirection(Direction.UP);
                elevator.setState(new MovingUpState());
            } else {
                elevator.setDirection(Direction.DOWN);
                elevator.setState(new MovingDownState());
            }
            elevator.setStatus(ElevatorStatus.MOVING);
        } else {
            elevator.setDirection(Direction.IDLE);
            elevator.setState(new IdleState());
            elevator.setStatus(ElevatorStatus.IDLE);
        }
    }

    @Override
    public String getStateName() {
        return "DOOR_OPEN";
    }
}
