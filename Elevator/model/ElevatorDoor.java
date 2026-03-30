package elevator.model;

import elevator.enums.DoorStatus;

public class ElevatorDoor {

    private DoorStatus status;

    public ElevatorDoor() {
        this.status = DoorStatus.CLOSED;
    }

    public void open() {
        this.status = DoorStatus.OPEN;
    }

    public void close() {
        this.status = DoorStatus.CLOSED;
    }

    public boolean isOpen() {
        return status == DoorStatus.OPEN;
    }

    public DoorStatus getStatus() {
        return status;
    }
}
