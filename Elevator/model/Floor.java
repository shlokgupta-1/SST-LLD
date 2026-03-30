package elevator.model;

public class Floor {

    private final int floorNumber;
    private final FloorPanel panel;
    private boolean underMaintenance;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.panel = new FloorPanel(floorNumber);
        this.underMaintenance = false;
    }

    public int getFloorNumber() { return floorNumber; }
    public FloorPanel getPanel() { return panel; }

    public boolean isUnderMaintenance() { return underMaintenance; }

    public void setUnderMaintenance(boolean underMaintenance) {
        this.underMaintenance = underMaintenance;
        this.panel.setUnderMaintenance(underMaintenance);
    }

    @Override
    public String toString() {
        return "Floor{number=" + floorNumber + ", maintenance=" + underMaintenance + "}";
    }
}
