package parkinglot.gates;

import parkinglot.models.Ticket;
import parkinglot.models.Vehicle;
import parkinglot.service.ParkingService;

public class EntryGate {

    private final String         gateId;
    private final ParkingService parkingService;

    public EntryGate(String gateId, ParkingService parkingService) {
        this.gateId         = gateId;
        this.parkingService = parkingService;
    }

    public Ticket processEntry(Vehicle vehicle) {
        System.out.printf("Entry Gate: %s%n", gateId);
        return parkingService.checkIn(vehicle, gateId);
    }

    public String getGateId() { return gateId; }
}
