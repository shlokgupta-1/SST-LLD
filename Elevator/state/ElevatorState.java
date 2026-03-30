package elevator.state;

import elevator.model.Elevator;

public interface ElevatorState {
    void move(Elevator elevator);
    void openDoor(Elevator elevator);
    void closeDoor(Elevator elevator);
    String getStateName();
}
