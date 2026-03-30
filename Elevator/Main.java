package elevator;

import elevator.controller.ElevatorController;
import elevator.enums.Direction;
import elevator.factory.ElevatorFactory;
import elevator.model.Building;
import elevator.model.Elevator;
import elevator.model.ExternalRequest;
import elevator.model.InternalRequest;
import elevator.service.ElevatorSystem;
import elevator.strategy.NearestElevatorStrategy;
import elevator.strategy.SCANStopSchedulingStrategy;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("ELEVATOR SYSTEM  —  BOOT");

        Building building = new Building("TechPark Tower", 25);

        Elevator e1 = ElevatorFactory.createElevatorAtFloor(1, 0);
        Elevator e2 = ElevatorFactory.createElevatorAtFloor(2, 10);
        Elevator e3 = ElevatorFactory.createElevatorAtFloor(3, 20);

        building.addElevator(e1);
        building.addElevator(e2);
        building.addElevator(e3);

        ElevatorController controller = new ElevatorController(
                List.of(e1, e2, e3),
                new NearestElevatorStrategy(),
                new SCANStopSchedulingStrategy()
        );

        ElevatorSystem system = new ElevatorSystem(building, controller);
        system.printStatus();

        separator("Scenario 1: SCAN Stop Scheduling");

        e1.setCurrentFloor(2);
        e1.addStop(20);
        e1.addStop(2);
        e1.addStop(0);
        e1.addStop(15);
        controller.dispatchElevator(e1);
        system.printStatus();

        separator("Scenario 2: Elevator Selection — Nearest + Direction");

        ExternalRequest req2 = new ExternalRequest(11, Direction.UP);
        system.requestElevator(req2);
        system.printStatus();

        separator("Scenario 3: Overload Detection & Alarm");

        System.out.println("Loading 800 kg into Elevator-3...");
        e3.getWeightSensor().addWeight(800.0);

        e3.addStop(5);
        System.out.println("Attempting to dispatch overloaded Elevator-3:");
        e3.openDoor();
        e3.closeDoor();

        System.out.println("\nPassengers exited — removing 200 kg.");
        e3.getWeightSensor().removeWeight(200.0);
        e3.getAlarm().reset();
        e3.closeDoor();
        System.out.println("  Weight now: " + e3.getWeightSensor());
        system.printStatus();

        separator("Scenario 4: Floor Under Maintenance");

        system.setFloorMaintenance(8, true);
        system.requestElevator(new ExternalRequest(8, Direction.UP));
        system.setFloorMaintenance(8, false);
        System.out.println("Floor 8 maintenance cleared.");

        separator("Scenario 5: Elevator Under Maintenance");

        system.setElevatorMaintenance(2, true);
        system.requestElevator(new ExternalRequest(9, Direction.DOWN));
        system.setElevatorMaintenance(2, false);
        System.out.println("Elevator-2 back in service.");
        system.printStatus();

        separator("Scenario 6: Internal Destination Request");

        e1.setCurrentFloor(5);
        system.selectDestination(new InternalRequest(5, 18, 1));
        system.printStatus();

        System.out.println("All scenarios completed!");
    }

    private static void separator(String title) {
        System.out.println(title);
    }
}
