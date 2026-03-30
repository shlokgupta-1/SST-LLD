package parkinglot.strategy;

import parkinglot.enums.SlotType;
import parkinglot.models.ParkingFloor;
import parkinglot.models.ParkingSlot;

import java.util.List;

public interface SlotAssignmentStrategy {
    ParkingSlot findSlot(List<ParkingFloor> floors, String entryGateId, SlotType slotType);
}
