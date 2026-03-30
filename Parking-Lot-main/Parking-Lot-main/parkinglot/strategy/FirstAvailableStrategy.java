package parkinglot.strategy;

import parkinglot.enums.SlotType;
import parkinglot.models.ParkingFloor;
import parkinglot.models.ParkingSlot;

import java.util.List;

public class FirstAvailableStrategy implements SlotAssignmentStrategy {

    @Override
    public ParkingSlot findSlot(List<ParkingFloor> floors,
                                String entryGateId,
                                SlotType slotType) {
        for (ParkingFloor floor : floors) {
            for (ParkingSlot slot : floor.getSlots()) {
                if (slot.getSlotType() == slotType && slot.tryOccupy()) {
                    return slot;
                }
            }
        }
        return null;
    }
}
