package elevator.model;

import java.util.ArrayList;
import java.util.List;

public class Building {

    private final String name;
    private final List<Floor> floors;
    private final List<Elevator> elevators;

    public Building(String name, int totalFloors) {
        this.name = name;
        this.floors = new ArrayList<>();
        this.elevators = new ArrayList<>();

        for (int i = 0; i < totalFloors; i++) {
            floors.add(new Floor(i));
        }
    }

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    public Floor getFloor(int floorNumber) {
        if (floorNumber < 0 || floorNumber >= floors.size()) return null;
        return floors.get(floorNumber);
    }

    public String getName() { return name; }
    public List<Floor> getFloors() { return floors; }
    public List<Elevator> getElevators() { return elevators; }

    @Override
    public String toString() {
        return "Building{name='" + name + "', floors=" + floors.size()
                + ", elevators=" + elevators.size() + "}";
    }
}
