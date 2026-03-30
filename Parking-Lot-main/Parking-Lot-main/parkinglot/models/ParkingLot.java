package parkinglot.models;

import parkinglot.enums.SlotType;
import parkinglot.enums.VehicleType;
import parkinglot.strategy.SlotAssignmentStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {

    private static volatile ParkingLot instance;

    private ParkingLot() {}

    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) {
                if (instance == null) {
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }

    private String name;
    private final List<ParkingFloor> floors     = new ArrayList<>();
    private final List<String>       entryGates = new ArrayList<>();
    private final List<String>       exitGates  = new ArrayList<>();

    private final ConcurrentHashMap<String, Ticket> activeTickets = new ConcurrentHashMap<>();

    private SlotAssignmentStrategy slotAssignmentStrategy;

    public void setName(String name) { this.name = name; }

    public void addFloor(ParkingFloor floor)  { floors.add(floor);      }
    public void addEntryGate(String gateId)   { entryGates.add(gateId); }
    public void addExitGate(String gateId)    { exitGates.add(gateId);  }

    public void setSlotAssignmentStrategy(SlotAssignmentStrategy strategy) {
        this.slotAssignmentStrategy = strategy;
    }

    public Ticket generateParkingTicket(Vehicle vehicle, String entryGateId) {
        SlotType required = mapVehicleTypeToSlotType(vehicle.getVehicleType());
        ParkingSlot slot  = slotAssignmentStrategy.findSlot(floors, entryGateId, required);

        if (slot == null) {
            throw new IllegalStateException(
                "No available " + required + " slot for vehicle: " + vehicle.getLicensePlate());
        }

        Ticket ticket = new Ticket(vehicle, slot, entryGateId);
        activeTickets.put(ticket.getTicketId(), ticket);
        return ticket;
    }

    public Ticket getTicket(String ticketId) {
        return activeTickets.get(ticketId);
    }

    public void closeTicket(String ticketId) {
        Ticket t = activeTickets.remove(ticketId);
        if (t != null) {
            t.getAssignedSlot().release();
        }
    }

    public void showStatus() {
        System.out.println("Parking lot: " + name);
        for (ParkingFloor floor : floors) {
            floor.printStatus();
        }
    }

    private SlotType mapVehicleTypeToSlotType(VehicleType vt) {
        return switch (vt) {
            case TWO_WHEELER -> SlotType.SMALL;
            case CAR         -> SlotType.MEDIUM;
            case BUS         -> SlotType.LARGE;
            default          -> SlotType.SMALL;
        };
    }

    public List<ParkingFloor> getFloors()     { return floors;     }
    public List<String>       getEntryGates() { return entryGates; }
    public List<String>       getExitGates()  { return exitGates;  }
    public String             getName()       { return name;       }
}
