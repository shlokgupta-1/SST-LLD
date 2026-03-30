package parkinglot.strategy;

import parkinglot.enums.SlotType;
import parkinglot.models.ParkingFloor;
import parkinglot.models.ParkingSlot;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class NearestSlotStrategy implements SlotAssignmentStrategy {

    @Override
    public ParkingSlot findSlot(List<ParkingFloor> floors,
                                String entryGateId,
                                SlotType slotType) {

        PriorityQueue<ParkingSlot> minHeap = new PriorityQueue<>(
            Comparator.comparingInt(s -> s.getDistance(entryGateId))
        );

        for (ParkingFloor floor : floors) {
            for (ParkingSlot slot : floor.getSlots()) {
                if (slot.getSlotType() == slotType && slot.isFree()) {
                    minHeap.offer(slot);
                }
            }
        }

        while (!minHeap.isEmpty()) {
            ParkingSlot candidate = minHeap.poll();
            if (candidate.tryOccupy()) {
                return candidate;
            }
        }

        return null;
    }
}
