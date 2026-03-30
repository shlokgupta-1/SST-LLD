package elevator.model;

import elevator.enums.Direction;

public class FloorPanel {

    private final int floorNumber;
    private boolean underMaintenance;

    public FloorPanel(int floorNumber) {
        this.floorNumber = floorNumber;
        this.underMaintenance = false;
    }

    public ExternalRequest pressButton(Direction direction) {
        if (underMaintenance) {
            System.out.println("  [FloorPanel-" + floorNumber
                    + "] 🚧 Floor under maintenance — button disabled.");
            return null;
        }
        System.out.println("  [FloorPanel-" + floorNumber
                + "] " + direction + " button pressed.");
        return new ExternalRequest(floorNumber, direction);
    }

    public int getFloorNumber() { return floorNumber; }

    public boolean isUnderMaintenance() { return underMaintenance; }

    public void setUnderMaintenance(boolean underMaintenance) {
        this.underMaintenance = underMaintenance;
        System.out.println("  [FloorPanel-" + floorNumber + "] Maintenance mode: " + underMaintenance);
    }
}
