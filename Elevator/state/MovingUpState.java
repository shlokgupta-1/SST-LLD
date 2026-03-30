package elevator.state;

import elevator.enums.Direction;
import elevator.enums.ElevatorStatus;
import elevator.model.Elevator;

public class MovingUpState implements ElevatorState {

    @Override
    public void move(Elevator elevator) {
        System.out.println("  [Elevator-" + elevator.getId() + "] Moving UP from floor " + elevator.getCurrentFloor());

        if (!elevator.getPendingStops().isEmpty()) {
            int nextStop = elevator.getPendingStops().first();
            elevator.setCurrentFloor(nextStop);
            elevator.getPendingStops().remove(nextStop);
            System.out.println("  [Elevator-" + elevator.getId() + "] Arrived at floor " + nextStop);
            openDoor(elevator);
        }

        if (elevator.getPendingStops().isEmpty()) {
            elevator.setDirection(Direction.IDLE);
            elevator.setState(new IdleState());
            elevator.setStatus(ElevatorStatus.IDLE);
        }
    }

    @Override
    public void openDoor(Elevator elevator) {
        if (elevator.getWeightSensor().isOverloaded()) {
            System.out.println("  [Elevator-" + elevator.getId() + "] ⚠️  Overloaded! Door stays open, alarm triggered.");
            elevator.getAlarm().trigger();
            return;
        }
        System.out.println("  [Elevator-" + elevator.getId() + "] Doors opening at floor " + elevator.getCurrentFloor());
        elevator.getDoor().open();
        elevator.setStatus(ElevatorStatus.DOOR_OPEN);
        closeDoor(elevator);
    }

    @Override
    public void closeDoor(Elevator elevator) {
        if (elevator.getWeightSensor().isOverloaded()) {
            System.out.println("  [Elevator-" + elevator.getId() + "] ⚠️  Cannot close — still overloaded!");
            elevator.getAlarm().trigger();
            return;
        }
        System.out.println("  [Elevator-" + elevator.getId() + "] Doors closing.");
        elevator.getDoor().close();
        elevator.setStatus(ElevatorStatus.MOVING);
    }

    @Override
    public String getStateName() {
        return "MOVING_UP";
    }
}
