package elevator.model;

import elevator.enums.RequestType;

public class InternalRequest extends ElevatorRequest {

    private final int destinationFloor;
    private final int elevatorId;

    public InternalRequest(int sourceFloor, int destinationFloor, int elevatorId) {
        super(sourceFloor, RequestType.INTERNAL);
        this.destinationFloor = destinationFloor;
        this.elevatorId = elevatorId;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    @Override
    public String toString() {
        return "InternalRequest{elevator=" + elevatorId
                + ", from=" + sourceFloor
                + ", to=" + destinationFloor + "}";
    }
}
