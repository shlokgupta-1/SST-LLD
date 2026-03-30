package elevator.model;

import elevator.enums.Direction;
import elevator.enums.ElevatorStatus;
import elevator.state.ElevatorState;
import elevator.state.IdleState;

import java.util.TreeSet;

public class Elevator {

    private final int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorStatus status;
    private ElevatorState state;

    private final ElevatorDoor door;
    private final WeightSensor weightSensor;
    private final Alarm alarm;
    private final TreeSet<Integer> pendingStops;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.status = ElevatorStatus.IDLE;
        this.state = new IdleState();
        this.door = new ElevatorDoor();
        this.weightSensor = new WeightSensor();
        this.alarm = new Alarm();
        this.pendingStops = new TreeSet<>();
    }

    public void move() {
        state.move(this);
    }

    public void openDoor() {
        state.openDoor(this);
    }

    public void closeDoor() {
        state.closeDoor(this);
    }

    public void addStop(int floor) {
        pendingStops.add(floor);
    }

    public int getId() { return id; }

    public int getCurrentFloor() { return currentFloor; }
    public void setCurrentFloor(int floor) { this.currentFloor = floor; }

    public Direction getDirection() { return direction; }
    public void setDirection(Direction direction) { this.direction = direction; }

    public ElevatorStatus getStatus() { return status; }
    public void setStatus(ElevatorStatus status) { this.status = status; }

    public ElevatorState getState() { return state; }
    public void setState(ElevatorState state) {
        System.out.println("  [Elevator-" + id + "] State: "
                + this.state.getStateName() + " → " + state.getStateName());
        this.state = state;
    }

    public ElevatorDoor getDoor() { return door; }
    public WeightSensor getWeightSensor() { return weightSensor; }
    public Alarm getAlarm() { return alarm; }
    public TreeSet<Integer> getPendingStops() { return pendingStops; }

    @Override
    public String toString() {
        return String.format("Elevator{id=%d, floor=%d, dir=%s, status=%s, stops=%s}",
                id, currentFloor, direction, status, pendingStops);
    }
}
