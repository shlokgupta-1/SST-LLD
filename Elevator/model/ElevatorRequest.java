package elevator.model;

import elevator.enums.RequestType;

public abstract class ElevatorRequest {

    protected final int sourceFloor;
    protected final RequestType requestType;

    protected ElevatorRequest(int sourceFloor, RequestType requestType) {
        this.sourceFloor = sourceFloor;
        this.requestType = requestType;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
