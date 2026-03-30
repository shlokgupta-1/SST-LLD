package elevator.model;

import elevator.enums.Direction;
import elevator.enums.RequestType;

public class ExternalRequest extends ElevatorRequest {

    private final Direction direction;

    public ExternalRequest(int sourceFloor, Direction direction) {
        super(sourceFloor, RequestType.EXTERNAL);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "ExternalRequest{floor=" + sourceFloor + ", direction=" + direction + "}";
    }
}
