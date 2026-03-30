package parkinglot.models;

import parkinglot.enums.SlotStatus;
import parkinglot.enums.SlotType;

import java.util.concurrent.locks.ReentrantLock;

public class ParkingSlot {

    private final String slotId;
    private final SlotType slotType;
    private final int floorNumber;
    private final int slotNumber;

    private final java.util.Map<String, Integer> distanceFromGate;

    private volatile SlotStatus status;
    private final ReentrantLock lock = new ReentrantLock();

    public ParkingSlot(String slotId, SlotType slotType,
                       int floorNumber, int slotNumber) {
        this.slotId      = slotId;
        this.slotType    = slotType;
        this.floorNumber = floorNumber;
        this.slotNumber  = slotNumber;
        this.status      = SlotStatus.FREE;
        this.distanceFromGate = new java.util.HashMap<>();
    }

    public void setDistance(String gateId, int distance) {
        distanceFromGate.put(gateId, distance);
    }

    public int getDistance(String gateId) {
        return distanceFromGate.getOrDefault(gateId, Integer.MAX_VALUE);
    }

    public boolean tryOccupy() {
        lock.lock();
        try {
            if (status == SlotStatus.FREE) {
                status = SlotStatus.OCCUPIED;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            status = SlotStatus.FREE;
        } finally {
            lock.unlock();
        }
    }

    public String    getSlotId()      { return slotId;      }
    public SlotType  getSlotType()    { return slotType;    }
    public int       getFloorNumber() { return floorNumber; }
    public int       getSlotNumber()  { return slotNumber;  }
    public SlotStatus getStatus()     { return status;      }
    public boolean   isFree()         { return status == SlotStatus.FREE; }

    @Override
    public String toString() {
        return String.format("Slot[id=%s, type=%s, status=%s]",
                slotId, slotType, status);
    }
}
