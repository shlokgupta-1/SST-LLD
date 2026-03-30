package elevator.observer;

public interface BuildingObserver {
    void onFloorMaintenanceChanged(int floorNumber, boolean underMaintenance);
    void onElevatorMaintenanceChanged(int elevatorId, boolean underMaintenance);
}
