package elevator.model;

import java.util.ArrayList;
import java.util.List;

public class ElevatorPanel {

    private final int elevatorId;
    private final int totalFloors;
    private final List<Integer> floorButtons;

    public ElevatorPanel(int elevatorId, int totalFloors) {
        this.elevatorId = elevatorId;
        this.totalFloors = totalFloors;
        this.floorButtons = new ArrayList<>();
        for (int i = 0; i < totalFloors; i++) {
            floorButtons.add(i);
        }
    }

    public int pressFloorButton(int floor) {
        if (floor < 0 || floor >= totalFloors) {
            System.out.println("  [ElevatorPanel-" + elevatorId + "] Invalid floor: " + floor);
            return -1;
        }
        System.out.println("  [ElevatorPanel-" + elevatorId + "] Button pressed for floor " + floor);
        return floor;
    }

    public int getElevatorId() { return elevatorId; }
    public int getTotalFloors() { return totalFloors; }
    public List<Integer> getFloorButtons() { return floorButtons; }
}
