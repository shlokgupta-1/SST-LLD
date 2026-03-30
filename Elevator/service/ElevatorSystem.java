package elevator.service;

import elevator.controller.ElevatorController;
import elevator.model.Building;
import elevator.model.Elevator;
import elevator.model.ExternalRequest;
import elevator.model.Floor;
import elevator.model.InternalRequest;
import elevator.observer.BuildingObserver;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {

    private final Building building;
    private final ElevatorController controller;
    private final List<BuildingObserver> observers = new ArrayList<>();

    public ElevatorSystem(Building building, ElevatorController controller) {
        this.building = building;
        this.controller = controller;
        subscribe(controller);
    }

    public void requestElevator(ExternalRequest request) {
        Floor floor = building.getFloor(request.getSourceFloor());
        if (floor != null && floor.isUnderMaintenance()) {
            System.out.println("⚠️  Floor-" + request.getSourceFloor()
                    + " is under maintenance — request rejected.");
            return;
        }
        controller.handleExternalRequest(request);
    }

    public void selectDestination(InternalRequest request) {
        controller.handleInternalRequest(request);
    }

    public void setFloorMaintenance(int floorNumber, boolean maintenance) {
        Floor floor = building.getFloor(floorNumber);
        if (floor == null) {
            System.out.println("⚠️  Floor-" + floorNumber + " not found.");
            return;
        }
        floor.setUnderMaintenance(maintenance);
        notifyFloorMaintenance(floorNumber, maintenance);
    }

    public void setElevatorMaintenance(int elevatorId, boolean maintenance) {
        notifyElevatorMaintenance(elevatorId, maintenance);
    }

    public void subscribe(BuildingObserver observer) {
        observers.add(observer);
    }

    private void notifyFloorMaintenance(int floorNumber, boolean maintenance) {
        observers.forEach(o -> o.onFloorMaintenanceChanged(floorNumber, maintenance));
    }

    private void notifyElevatorMaintenance(int elevatorId, boolean maintenance) {
        observers.forEach(o -> o.onElevatorMaintenanceChanged(elevatorId, maintenance));
    }

    public void printStatus() {
        System.out.println("\n========== ELEVATOR SYSTEM STATUS ==========");
        for (Elevator e : controller.getElevators()) {
            System.out.println("  " + e);
        }
        System.out.println("=============================================\n");
    }

    public Building getBuilding() { return building; }
    public ElevatorController getController() { return controller; }
}
