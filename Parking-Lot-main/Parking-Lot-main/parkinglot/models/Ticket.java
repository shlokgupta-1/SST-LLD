package parkinglot.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {

    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot assignedSlot;
    private final String entryGateId;
    private final LocalDateTime entryTime;

    public Ticket(Vehicle vehicle, ParkingSlot assignedSlot, String entryGateId) {
        this.ticketId     = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.vehicle      = vehicle;
        this.assignedSlot = assignedSlot;
        this.entryGateId  = entryGateId;
        this.entryTime    = LocalDateTime.now();
    }

    public String        getTicketId()     { return ticketId;     }
    public Vehicle       getVehicle()      { return vehicle;      }
    public ParkingSlot   getAssignedSlot() { return assignedSlot; }
    public String        getEntryGateId()  { return entryGateId;  }
    public LocalDateTime getEntryTime()    { return entryTime;    }

    @Override
    public String toString() {
        return "Ticket ID: " + ticketId
            + ", Vehicle: " + vehicle.getLicensePlate()
            + ", Gate: " + entryGateId
            + ", Slot: " + assignedSlot.getSlotId()
            + ", Floor: " + assignedSlot.getFloorNumber()
            + ", Entry: " + entryTime.toString().replace("T", " ").substring(0, 16);
    }
}
