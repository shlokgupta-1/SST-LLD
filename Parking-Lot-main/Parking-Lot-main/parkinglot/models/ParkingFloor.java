package parkinglot.models;

import parkinglot.enums.SlotStatus;
import parkinglot.enums.SlotType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ParkingFloor {

    private final int floorNumber;
    private final List<ParkingSlot> slots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.slots       = new ArrayList<>();
    }

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }

    public Map<SlotType, Integer> getFreeCountByType() {
        Map<SlotType, Integer> counts = new EnumMap<>(SlotType.class);
        for (SlotType type : SlotType.values()) {
            counts.put(type, 0);
        }
        for (ParkingSlot slot : slots) {
            if (slot.isFree()) {
                counts.merge(slot.getSlotType(), 1, Integer::sum);
            }
        }
        return counts;
    }

    public void printStatus() {
        Map<SlotType, Integer> free  = getFreeCountByType();
        Map<SlotType, Integer> total = getTotalCountByType();

        System.out.println("Floor " + floorNumber
            + " -> Small: " + free.get(SlotType.SMALL) + "/" + total.get(SlotType.SMALL)
            + "  Medium: " + free.get(SlotType.MEDIUM) + "/" + total.get(SlotType.MEDIUM)
            + "  Large: " + free.get(SlotType.LARGE) + "/" + total.get(SlotType.LARGE));
    }

    private Map<SlotType, Integer> getTotalCountByType() {
        Map<SlotType, Integer> counts = new EnumMap<>(SlotType.class);
        for (SlotType type : SlotType.values()) {
            counts.put(type, 0);
        }
        for (ParkingSlot slot : slots) {
            counts.merge(slot.getSlotType(), 1, Integer::sum);
        }
        return counts;
    }

    public int getFloorNumber() { return floorNumber; }
}
