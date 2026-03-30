package elevator.factory;

import elevator.model.Elevator;

public class ElevatorFactory {

    private ElevatorFactory() {}

    public static Elevator createStandardElevator(int id) {
        return new Elevator(id);
    }

    public static Elevator createElevatorAtFloor(int id, int startFloor) {
        Elevator elevator = new Elevator(id);
        elevator.setCurrentFloor(startFloor);
        return elevator;
    }
}
